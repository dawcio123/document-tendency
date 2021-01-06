package com.dawid.documenttendency.service;

import com.dawid.documenttendency.model.DocumentOpenNotificationDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQListener {

    public static final String DOCUMENT_OPEN = "document_open";
  private RabbitTemplate rabbitTemplate;

    public RabbitMQListener(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = DOCUMENT_OPEN)
    public void documentOpenNotificationListener(DocumentOpenNotificationDTO documentOpenNotificationDTO){
        System.out.println(documentOpenNotificationDTO.toString());
    }
}
