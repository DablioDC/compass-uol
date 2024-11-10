package br.com.dabliodc.compass_uol.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SqsQueueService implements QueueService {

    private final AmazonSQS sqs;
    private final ObjectMapper objectMapper;

    public SqsQueueService(AmazonSQS sqs, ObjectMapper objectMapper) {
        this.sqs = sqs;
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> T sendToQueue(String queueUrl, T message) {
        try {
            String messageBody = objectMapper.writeValueAsString(message);
            SendMessageRequest request = new SendMessageRequest(queueUrl, messageBody);
            sqs.sendMessage(request);
            return message;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar mensagem para a fila", e);
        }
    }
}

