package br.com.dabliodc.compass_uol.service;

import br.com.dabliodc.compass_uol.domain.PaymentDTO;
import br.com.dabliodc.compass_uol.model.Charge;
import br.com.dabliodc.compass_uol.utils.PaymentStatus;
import org.springframework.stereotype.Component;

@Component
public class ExceedPaymentValidation implements PaymentValidationStrategy {
    @Override
    public boolean validate(PaymentDTO payment, Charge charge) {
        return charge.getValueCharge().compareTo(payment.getAmountPaid()) < 0;
    }

    @Override
    public PaymentStatus getPaymentType() {
        return PaymentStatus.EXCEDENTE;
    }

    @Override
    public String getQueueName() {
        return "excedenteQueue";
    }
}
