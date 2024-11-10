package br.com.dabliodc.compass_uol.service;

import br.com.dabliodc.compass_uol.domain.PaymentDTO;
import br.com.dabliodc.compass_uol.domain.VendorDTO;
import br.com.dabliodc.compass_uol.model.Charge;
import br.com.dabliodc.compass_uol.model.Vendor;
import br.com.dabliodc.compass_uol.repository.ChargeRepository;
import br.com.dabliodc.compass_uol.repository.PaymentRepository;
import br.com.dabliodc.compass_uol.repository.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentServiceImpTest {
    @Mock
    private ObjectMapperService objectMapperService;

    @Mock
    private ChargeRepository chargeRepository;

    @Mock
    private VendorRepository vendorRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private ValidatePaymentServiceImp validatePaymentServiceImp;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private PaymentServiceImp paymentServiceImp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void validatePaymentShouldReturn200WhenPaymentIsValid() {
        Vendor vendor = createVendor("VD001");
        vendorRepository.save(vendor);
        Charge charge = createCharge("CHG001", vendor, BigDecimal.valueOf(1500));
        chargeRepository.save(charge);

        VendorDTO vendorDTO = createVendorDTO(vendor.getId(), charge.getId(), charge.getValueCharge());

        Mockito.when(vendorRepository.findById("VD001")).thenReturn(Optional.of(vendor));
        Mockito.when(chargeRepository.findByIdAndVendor_Id("CHG001", "VD001")).thenReturn(Optional.of(charge));

        ResponseEntity<Object> response = paymentServiceImp.validatePayment(vendorDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void validatePaymentShouldReturn404WhenVendorNotFound() {
        Object inputObject = new Object();
        VendorDTO vendorDTO = new VendorDTO("VD002", List.of());
        Mockito.when(objectMapperService.mapperPayments(inputObject)).thenReturn(vendorDTO);
        Mockito.when(vendorRepository.findById("VD002")).thenReturn(Optional.empty());
        Mockito.when(messageSource.getMessage("error.vendor.notfound", new Object[]{"VD002"}, Locale.getDefault())).thenReturn("Vendor not found");

        ResponseEntity<Object> response = paymentServiceImp.validatePayment(inputObject);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Vendor not found", response.getBody());
    }

    @Test
    void validatePaymentShouldReturn400WhenExceptionOccurs() {
        Object inputObject = new Object();
        Mockito.when(objectMapperService.mapperPayments(inputObject)).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<Object> response = paymentServiceImp.validatePayment(inputObject);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro ao processar o pagamento: Unexpected error", response.getBody());
    }

    public Vendor createVendor(String vendor_id){
        return new Vendor(vendor_id);
    }

    public Charge createCharge(String idCharge, Vendor vendor, BigDecimal valueCharge){
        Charge charge = new Charge().builder()
                .setId(idCharge)
                .setVendor(vendor)
                .setPayDay(LocalDate.now())
                .setValueCharge(valueCharge)
                .setPaymentStatusEnum(null);
        return charge;
    }

    public VendorDTO createVendorDTO(String idVendor, String idPayment, BigDecimal valuePayment){
        return new VendorDTO(idVendor, List.of(new PaymentDTO(idPayment, LocalDate.now(), valuePayment, null)));
    }
}
