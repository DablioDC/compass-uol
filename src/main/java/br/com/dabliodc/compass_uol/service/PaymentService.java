package br.com.dabliodc.compass_uol.service;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PaymentService {
    ResponseEntity<Object> validatePayment(Object object);
    ResponseEntity<List<Object>> searchCharges(Object object);
}
