package br.com.dabliodc.compass_uol.service;

import br.com.dabliodc.compass_uol.domain.PaymentDTO;
import br.com.dabliodc.compass_uol.domain.VendorDTO;
import br.com.dabliodc.compass_uol.model.Charge;
import br.com.dabliodc.compass_uol.model.Vendor;
import br.com.dabliodc.compass_uol.repository.ChargeRepository;
import br.com.dabliodc.compass_uol.repository.VendorRepository;
import br.com.dabliodc.compass_uol.utils.PaymentStatusEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class PaymentServiceImpTest {

    @Mock
    private ChargeRepository chargeRepository;

    @Mock
    private VendorRepository vendorRepository;

    @Mock
    private PaymentServiceImp paymentServiceImp;

    @Mock
    private ValidatePaymentServiceImp validatePaymentServiceImp;

    @Test
    void vendorNotFound() {
        VendorDTO vendorDTO = new VendorDTO("XW001", List.of(
                new PaymentDTO("GRE001", LocalDate.now(), BigDecimal.valueOf(100), null)));
        Mockito.when(paymentServiceImp.validatePayment(vendorDTO)).thenReturn(null);
    }

    @Test
    void chargeNotFound() {
        VendorDTO vendorDTO = new VendorDTO("WD001", List.of(
                new PaymentDTO("T1001", LocalDate.now(), BigDecimal.valueOf(100), null)));
        Mockito.when(paymentServiceImp.validatePayment(vendorDTO)).thenReturn(null);
    }

    @Test
    void validatePaymentTotal() {
        Vendor vendor = createVendor("VT001");
        vendorRepository.save(vendor);
        Charge charge = createCharge("CT001", vendor, BigDecimal.valueOf(1000));
        chargeRepository.save(charge);
        PaymentDTO paymentDTO = createPaymentDTO("CT001", BigDecimal.valueOf(1000));
        PaymentDTO validatedPaymentDTO = createPaymentDTO("CT001", BigDecimal.valueOf(1000));

        validatedPaymentDTO.setPaymentStatusEnum(PaymentStatusEnum.TOTAL);
        Mockito.when(validatePaymentServiceImp.validateAndSend(paymentDTO, charge)).thenReturn(validatedPaymentDTO);

        PaymentDTO result = validatePaymentServiceImp.validateAndSend(paymentDTO, charge);

        assertEquals(PaymentStatusEnum.TOTAL, result.getPaymentStatusEnum());
    }

    @Test
    void validatePaymentPartial() {
        Vendor vendor = createVendor("VT001");
        vendorRepository.save(vendor);
        Charge charge = createCharge("CT001", vendor, BigDecimal.valueOf(1500));
        chargeRepository.save(charge);
        PaymentDTO paymentDTO = createPaymentDTO("CT001", BigDecimal.valueOf(1000));
        PaymentDTO validatedPaymentDTO = createPaymentDTO("CT001", BigDecimal.valueOf(1000));

        validatedPaymentDTO.setPaymentStatusEnum(PaymentStatusEnum.PARCIAL);
        Mockito.when(validatePaymentServiceImp.validateAndSend(paymentDTO, charge)).thenReturn(validatedPaymentDTO);

        PaymentDTO result = validatePaymentServiceImp.validateAndSend(paymentDTO, charge);

        assertEquals(PaymentStatusEnum.PARCIAL, result.getPaymentStatusEnum());
    }

    @Test
    void validatePaymentExceed() {
        Vendor vendor = createVendor("VT001");
        vendorRepository.save(vendor);
        Charge charge = createCharge("CT001", vendor, BigDecimal.valueOf(1500));
        chargeRepository.save(charge);
        PaymentDTO paymentDTO = createPaymentDTO("CT001", BigDecimal.valueOf(2000));
        PaymentDTO validatedPaymentDTO = createPaymentDTO("CT001", BigDecimal.valueOf(2000));

        validatedPaymentDTO.setPaymentStatusEnum(PaymentStatusEnum.EXCEDENTE);
        Mockito.when(validatePaymentServiceImp.validateAndSend(paymentDTO, charge)).thenReturn(validatedPaymentDTO);

        PaymentDTO result = validatePaymentServiceImp.validateAndSend(paymentDTO, charge);
        assertEquals(PaymentStatusEnum.EXCEDENTE, result.getPaymentStatusEnum());
    }

    public Vendor createVendor(String vendor_id){
        return new Vendor(vendor_id);
    }

    public Charge createCharge(String idCharge, Vendor vendor, BigDecimal valueCharge){
        return new Charge().builder()
                .setId(idCharge)
                .setVendor(vendor)
                .setPayDay(LocalDate.now())
                .setValueCharge(valueCharge)
                .setPaymentStatusEnum(null);
    }

    public PaymentDTO createPaymentDTO(String id, BigDecimal amountPaid){
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(id);
        paymentDTO.setPayDay(LocalDate.now());
        paymentDTO.setAmountPaid(amountPaid);
        return paymentDTO;
    }
}
