package com.dawid.documenttendency.service;

import com.dawid.documenttendency.model.DocumentOpenInfo;
import com.dawid.documenttendency.model.DocumentOpenNotificationDTO;


public interface DocumentOpenInfoService {

    void saveDocumentOpenInfo(DocumentOpenNotificationDTO documentOpenNotificationDTO);
}
