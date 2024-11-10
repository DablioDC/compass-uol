package br.com.dabliodc.compass_uol.service;

import br.com.dabliodc.compass_uol.domain.PaymentDTO;
import br.com.dabliodc.compass_uol.domain.VendorDTO;
import br.com.dabliodc.compass_uol.domain.exception.ChargeNotFoundException;
import br.com.dabliodc.compass_uol.domain.exception.VendorNotFoundException;
import br.com.dabliodc.compass_uol.model.Charge;
import br.com.dabliodc.compass_uol.model.Payment;
import br.com.dabliodc.compass_uol.model.Vendor;
import br.com.dabliodc.compass_uol.repository.ChargeRepository;
import br.com.dabliodc.compass_uol.repository.PaymentRepository;
import br.com.dabliodc.compass_uol.repository.VendorRepository;
import br.com.dabliodc.compass_uol.utils.PaymentStatusEnum;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class PaymentServiceImp implements PaymentService {
    @Autowired
    private final ObjectMapperService objectMapperService;
    @Autowired
    private final ChargeRepository chargeRepository;
    @Autowired
    private final VendorRepository vendorRepository;
    @Autowired
    private final PaymentRepository paymentRepository;
    @Autowired
    private final ValidatePaymentServiceImp validatePaymentServiceImp;
    @Autowired
    private final MessageSource messageSource;

    @Override
    public ResponseEntity<Object> validatePayment(Object object) {
        try {
            VendorDTO vendorDTO = objectMapperService.mapperPayments(object);

            Vendor vendor = validateVendor(vendorDTO);
            List<Charge> chargeList = validateCharge(vendorDTO);
            var vendorDTOUpdate = processPayments(vendorDTO, vendor, chargeList);

            return ResponseEntity.ok(vendorDTOUpdate);

        } catch (VendorNotFoundException | ChargeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao processar o pagamento: " + e.getMessage());
        }
    }

    private Vendor validateVendor(VendorDTO vendorDTO) {
        return vendorRepository.findById(vendorDTO.getId())
                .orElseThrow(() -> new VendorNotFoundException(
                        messageSource.getMessage("error.vendor.notfound", new Object[]{vendorDTO.getId()}, Locale.forLanguageTag("pt-BR"))));
    }

    private List<Charge> validateCharge(VendorDTO vendorDTO) {
        List<Charge> chargeList = new ArrayList<>();
        for (PaymentDTO paymentDTO : vendorDTO.getPaymentDTOS()) {
            chargeList.add(chargeRepository.findByIdAndVendor_Id(paymentDTO.getId(), vendorDTO.getId())
                    .orElseThrow(() -> new ChargeNotFoundException(
                            messageSource.getMessage("error.charge.notfound", new Object[]{paymentDTO.getId()}, Locale.getDefault()))));
        }
        return chargeList;
    }

    private VendorDTO processPayments(VendorDTO vendorDTO, Vendor vendor, List<Charge> chargeList) {
        List<PaymentDTO> updatedPayments = vendorDTO.getPaymentDTOS().stream()
                .map(paymentDTO -> processSinglePayment(paymentDTO, vendor, chargeList))
                .collect(Collectors.toList());
        return new VendorDTO(vendor.getId(), updatedPayments);
    }

    private PaymentDTO processSinglePayment(PaymentDTO paymentDTO, Vendor vendor, List<Charge> chargeList) {
        Charge charge = chargeList.stream()
                .filter(ch -> ch.getId().equals(paymentDTO.getId()))
                .findFirst()
                .orElse(null);
        PaymentDTO updatedPayment = validatePaymentServiceImp.validateAndSend(paymentDTO, charge);
        savePayment(updatedPayment, vendor);
        updateCharge(charge, updatedPayment.getPaymentStatusEnum());
        return updatedPayment;
    }

    private void savePayment(PaymentDTO paymentDTO, Vendor vendor) {
        var payment = new Payment().builder()
                .setId(paymentDTO.getId())
                .setVendor(vendor)
                .setPayDay(paymentDTO.getPayDay())
                .setAmountPaid(paymentDTO.getAmountPaid())
                .setPaymentStatusEnum(paymentDTO.getPaymentStatusEnum());
        paymentRepository.save(payment);
    }

    private void updateCharge(Charge charge, PaymentStatusEnum paymentStatusEnum) {
        charge.setPaymentStatusEnum(paymentStatusEnum);
        chargeRepository.save(charge);
    }
}

