package br.com.dabliodc.compass_uol.service;

import br.com.dabliodc.compass_uol.domain.VendorDTO;
import org.springframework.http.ResponseEntity;

public interface PaymentService {
    ResponseEntity<Object> validatePayment(VendorDTO vendorDTO);
}
