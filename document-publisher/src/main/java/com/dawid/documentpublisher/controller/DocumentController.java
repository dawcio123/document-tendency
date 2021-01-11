package com.dawid.documentpublisher.controller;

import com.dawid.documentpublisher.model.document.Document;
import com.dawid.documentpublisher.repository.DocumentRepository;
import com.dawid.documentpublisher.service.NotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    private NotificationService notificationService;
    private DocumentRepository documentRepository;

    public DocumentController(NotificationService notificationService, DocumentRepository documentRepository) {
        this.notificationService = notificationService;
        this.documentRepository = documentRepository;
    }


    @GetMapping("/create")
    public Document createDocuments(){
        for (int i = 0; i <100; i++  ){
            Document document = documentRepository.createDocument();
            notificationService.sendOpenDocumentNotification(document);
        }
        Document document = documentRepository.createDocument();
        notificationService.sendOpenDocumentNotification(document);

        return document;
    }
}

