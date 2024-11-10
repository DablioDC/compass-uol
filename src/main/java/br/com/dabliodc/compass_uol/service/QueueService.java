package br.com.dabliodc.compass_uol.service;

public interface QueueService {
    <T> T sendToQueue(String queueUrl, T message);
}
