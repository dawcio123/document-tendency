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
        documentOpenNotification = createDocOpenNotificationForTrend(document);
        rabbitTemplate.convertAndSend(DOCUMENT_OPEN, documentOpenNotification);
        documentOpenNotification = createDocOpenNotificationForTest(document);
        rabbitTemplate.convertAndSend(DOCUMENT_OPEN, documentOpenNotification);
    }

    private DocumentOpenNotification createDocOpenNotification(Document document) {
        return DocumentOpenNotification.builder()
                .documentId(document.getId())
                .userId(UUID.randomUUID().toString())
                .openDate(getFirstDayOfPreviousWeek().plusDays(Long.valueOf(new Random().nextInt(7))))
                .build();
    }

    private DocumentOpenNotification createDocOpenNotificationForTrend(Document document) {
        return DocumentOpenNotification.builder()
                .documentId(document.getId())
                .userId(UUID.randomUUID().toString())
                .openDate(getFirstDayOfPreviousWeek().plusDays(Long.valueOf(new Random().nextInt(3)+4)))
                .build();
    }

    private DocumentOpenNotification createDocOpenNotificationForTest(Document document) {
        return DocumentOpenNotification.builder()
                .documentId(document.getId())
                .userId(UUID.randomUUID().toString())
                .openDate(LocalDate.of (2020, 12,  new Random().nextInt(30)+1))
                .build();
    }




    private LocalDate getFirstDayOfPreviousWeek() {
        int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();
        LocalDate BegOfThisWeek = LocalDate.now().minusDays(dayOfWeek - 1);
        LocalDate fromDate = BegOfThisWeek.minusWeeks(1);
        return fromDate;
    }

}
