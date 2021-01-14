package com.dawid.documenttendency.model.openNotification;

import java.time.LocalDate;
import java.util.List;


public interface DocumentOpenInfoService {

    void saveDocumentOpenInfo(DocumentOpenNotification documentOpenNotificationDTO);



    List<DocumentOpenInfo> getDocumentOpenInfoFromRange(LocalDate from, LocalDate toDate);

    List<String> getDocumentsIds(LocalDate fromDate, LocalDate toDate);
}
