package br.com.dabliodc.compass_uol.service;

import br.com.dabliodc.compass_uol.domain.PaymentDTO;
import br.com.dabliodc.compass_uol.model.Charge;

public interface ValidatePaymentService {
    PaymentDTO validateAndSend(PaymentDTO payment, Charge charge);
}
