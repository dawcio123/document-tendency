package com.dawid.documenttendency.model.openNotification;

import com.dawid.documenttendency.model.openNotification.DocumentOpenInfo;
import com.dawid.documenttendency.model.openNotification.DocumentOpenNotification;

import java.time.LocalDate;
import java.util.List;


public interface DocumentOpenInfoService {

    void saveDocumentOpenInfo(DocumentOpenNotification documentOpenNotificationDTO);



    List<DocumentOpenInfo> getDocumentOpenInfoFromRange(LocalDate from, LocalDate toDate);

    List<String> getDocumentsIds(LocalDate fromDate, LocalDate toDate);
}
