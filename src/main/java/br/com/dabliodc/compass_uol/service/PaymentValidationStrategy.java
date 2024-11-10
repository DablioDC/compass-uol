package br.com.dabliodc.compass_uol.service;

import br.com.dabliodc.compass_uol.domain.PaymentDTO;
import br.com.dabliodc.compass_uol.model.Charge;
import br.com.dabliodc.compass_uol.utils.PaymentStatusEnum;

public interface PaymentValidationStrategy {
    boolean validate(PaymentDTO payment, Charge charge);
    PaymentStatusEnum getPaymentType();
    String getQueueName();
}
