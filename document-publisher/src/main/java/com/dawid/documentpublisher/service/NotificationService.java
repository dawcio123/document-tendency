package com.dawid.documentpublisher.service;

import com.dawid.documentpublisher.model.document.Document;

public interface NotificationService {

    void sendOpenDocumentNotification(Document document);
}
