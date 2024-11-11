package br.com.dabliodc.compass_uol.controller;

import br.com.dabliodc.compass_uol.domain.VendorDTO;
import br.com.dabliodc.compass_uol.service.PaymentServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private final PaymentServiceImp chargeService;

    @PostMapping(value = "/send", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> sendPayments(@RequestBody @Validated VendorDTO vendorDTO){
        return chargeService.validatePayment(vendorDTO);
    }
}
