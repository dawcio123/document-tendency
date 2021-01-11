package com.dawid.documentpublisher.service;

import com.dawid.documentpublisher.model.document.Document;
import com.dawid.documentpublisher.model.notification.DocumentOpenNotification;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@Service
public class NotificationServiceImpl implements NotificationService {

    public static final String DOCUMENT_OPEN = "document_open";
    private RabbitTemplate rabbitTemplate;

    public NotificationServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOpenDocumentNotification(Document document) {
        DocumentOpenNotification documentOpenNotification = createDocOpenNotification(document);
        rabbitTemplate.convertAndSend(DOCUMENT_OPEN, documentOpenNotification);
    }

    private DocumentOpenNotification createDocOpenNotification(Document document) {
        return DocumentOpenNotification.builder()
                .documentId(document.getId())
                .userId(UUID.randomUUID().toString())
                .openDate(LocalDate.of(2021, 1,new Random().nextInt(30) + 1))
                .build();
    }
}
