package com.dawid.documenttendency.service;

import com.dawid.documenttendency.model.DocumentOpenNotification;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQListener {

    public static final String DOCUMENT_OPEN = "document_open";
    private RabbitTemplate rabbitTemplate;
    private DocumentOpenInfoService documentOpenInfoService;

    public RabbitMQListener(RabbitTemplate rabbitTemplate, DocumentOpenInfoService documentOpenInfoService) {
        this.rabbitTemplate = rabbitTemplate;
        this.documentOpenInfoService = documentOpenInfoService;

    }

    @RabbitListener(queues = DOCUMENT_OPEN)
    public void documentOpenNotificationListener(DocumentOpenNotification documentOpenNotification) {
        documentOpenInfoService.saveDocumentOpenInfo(documentOpenNotification);
    }
}
