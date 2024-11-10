package br.com.dabliodc.compass_uol.controller;

import br.com.dabliodc.compass_uol.service.PaymentServiceImp;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentServiceImp chargeService;

    public PaymentController(PaymentServiceImp chargeService){
        this.chargeService = chargeService;
    }

    @PostMapping(value = "/send", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> sendPayments(@RequestBody Object object){
        return chargeService.validatePayment(object);
    }
}
