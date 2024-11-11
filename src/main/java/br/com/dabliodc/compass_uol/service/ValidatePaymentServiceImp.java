package br.com.dabliodc.compass_uol.service;

import br.com.dabliodc.compass_uol.domain.PaymentDTO;
import br.com.dabliodc.compass_uol.model.Charge;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ValidatePaymentServiceImp implements ValidatePaymentService {
    @Autowired
    private final MockQueueService queueService;
    @Autowired
    private final List<PaymentValidationStrategy> validationStrategies;

    @Override
    public PaymentDTO validateAndSend(PaymentDTO payment, Charge charge) {
        for (PaymentValidationStrategy strategy : validationStrategies) {
            if (strategy.validate(payment, charge)) {
                payment.setPaymentStatusEnum(strategy.getPaymentType());
                return queueService.sendToQueue(strategy.getQueueName(), payment);
            }
        }
        throw new IllegalArgumentException("Tipo de pagamento não suportado para o valor informado.");
    }
}

