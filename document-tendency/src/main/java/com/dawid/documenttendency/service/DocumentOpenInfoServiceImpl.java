package com.dawid.documenttendency.service;

import com.dawid.documenttendency.model.DocumentOpenInfo;
import com.dawid.documenttendency.model.DocumentOpenNotificationDTO;
import com.dawid.documenttendency.repository.DocumentOpenInfoRepository;
import org.springframework.stereotype.Service;

@Service
public class DocumentOpenInfoServiceImpl implements DocumentOpenInfoService {

    private DocumentOpenInfoRepository documentOpenInfoRepository;

    public DocumentOpenInfoServiceImpl(DocumentOpenInfoRepository documentOpenInfoRepository) {
        this.documentOpenInfoRepository = documentOpenInfoRepository;
    }

    public void saveDocumentOpenInfo(DocumentOpenNotificationDTO documentOpenNotificationDTO) {
        DocumentOpenInfo documentOpenInfo =  DocumentOpenInfo.builder()
                .documentId(documentOpenNotificationDTO.getDocumentId())
                .userId(documentOpenNotificationDTO.getUserId())
                .openDate(documentOpenNotificationDTO.getOpenDate())
                .build();

        documentOpenInfoRepository.save(documentOpenInfo);
    }
}
