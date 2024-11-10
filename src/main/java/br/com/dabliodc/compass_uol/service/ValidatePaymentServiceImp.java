package br.com.dabliodc.compass_uol.service;

import br.com.dabliodc.compass_uol.domain.PaymentDTO;
import br.com.dabliodc.compass_uol.model.Charge;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ValidatePaymentServiceImp implements ValidatePaymentService {
    private final MockQueueService queueService;
    private final List<PaymentValidationStrategy> validationStrategies;

    public ValidatePaymentServiceImp(MockQueueService queueService, List<PaymentValidationStrategy> validationStrategies) {
        this.queueService = queueService;
        this.validationStrategies = validationStrategies;
    }

    @Override
    public PaymentDTO validateAndSend(PaymentDTO payment, Charge charge) {
        for (PaymentValidationStrategy strategy : validationStrategies) {
            if (strategy.validate(payment, charge)) {
                payment.setPaymentStatus(strategy.getPaymentType());
                return queueService.sendToQueue(strategy.getQueueName(), payment);
            }
        }
        throw new IllegalArgumentException("Tipo de pagamento não suportado para o valor informado.");
    }
}

