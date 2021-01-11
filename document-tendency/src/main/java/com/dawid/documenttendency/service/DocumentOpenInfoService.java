package com.dawid.documenttendency.service;

import com.dawid.documenttendency.model.DocumentOpenInfo;
import com.dawid.documenttendency.model.DocumentOpenNotification;

import java.time.LocalDate;
import java.util.List;


public interface DocumentOpenInfoService {

    void saveDocumentOpenInfo(DocumentOpenNotification documentOpenNotificationDTO);



    List<DocumentOpenInfo> getDocumentOpenInfoFromRange(LocalDate from, LocalDate toDate);
}
