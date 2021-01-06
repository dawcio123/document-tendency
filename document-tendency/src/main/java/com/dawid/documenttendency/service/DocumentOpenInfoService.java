package com.dawid.documenttendency.service;

import com.dawid.documenttendency.model.DocumentOpenInfo;
import com.dawid.documenttendency.model.DocumentOpenNotificationDTO;

import java.util.List;


public interface DocumentOpenInfoService {

    void saveDocumentOpenInfo(DocumentOpenNotificationDTO documentOpenNotificationDTO);

    List<DocumentOpenInfo> getAll();

    List<DocumentOpenInfo> getPopular();
}
