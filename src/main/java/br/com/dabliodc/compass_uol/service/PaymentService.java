package br.com.dabliodc.compass_uol.service;

import org.springframework.http.ResponseEntity;

public interface PaymentService {
    ResponseEntity<Object> validatePayment(Object object);
}
