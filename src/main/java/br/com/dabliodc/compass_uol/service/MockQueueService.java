package br.com.dabliodc.compass_uol.service;

import br.com.dabliodc.compass_uol.domain.PaymentDTO;
import org.springframework.stereotype.Service;

@Service
public class MockQueueService implements QueueService {
    @Override
    public <T> T sendToQueue(String queueUrl, T message) {
        if (message instanceof PaymentDTO payment) {
            payment.setPaymentStatusEnum(payment.getPaymentStatusEnum());
        }
        return message;
    }
}

