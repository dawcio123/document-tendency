package com.dawid.documenttendency.service;

import com.dawid.documenttendency.model.Document;
import com.dawid.documenttendency.model.DocumentOpenInfo;
import com.dawid.documenttendency.model.DocumentOpenNotificationDTO;
import com.dawid.documenttendency.repository.DocumentOpenInfoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DocumentOpenInfoServiceImpl implements DocumentOpenInfoService {

    private DocumentOpenInfoRepository documentOpenInfoRepository;
    private TrendService trendService;

    public DocumentOpenInfoServiceImpl(DocumentOpenInfoRepository documentOpenInfoRepository, TrendService trendService) {
        this.documentOpenInfoRepository = documentOpenInfoRepository;
        this.trendService = trendService;
    }

    public void saveDocumentOpenInfo(DocumentOpenNotificationDTO documentOpenNotificationDTO) {
        DocumentOpenInfo documentOpenInfo =  DocumentOpenInfo.builder()
                .documentId(documentOpenNotificationDTO.getDocumentId())
                .userId(documentOpenNotificationDTO.getUserId())
                .openDate(documentOpenNotificationDTO.getOpenDate())
                .build();

        documentOpenInfoRepository.save(documentOpenInfo);
    }


    public List<DocumentOpenInfo> getAll() {
        return documentOpenInfoRepository.findAll();
    }





}
