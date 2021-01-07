package com.dawid.documenttendency.service;

import com.dawid.documenttendency.model.Document;
import com.dawid.documenttendency.model.DocumentOpenInfo;
import com.dawid.documenttendency.model.DocumentOpenNotificationDTO;
import com.dawid.documenttendency.repository.DocumentOpenInfoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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


    public List<DocumentOpenInfo> getAll() {
        return documentOpenInfoRepository.findAll();
    }


    public List<Document> getPopular() {

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(7);
        List<DocumentOpenInfo> documentsInRange = documentOpenInfoRepository.findAllByOpenDateIsBetween(startDate, endDate);

        Map<Long, Long> popularDocumentById = calculatePopularity(documentsInRange);

        Map<Long, Long> topTen = sortPopularity(popularDocumentById);


        topTen.entrySet().stream()
               // .sorted(Map.Entry.comparingByValue())
                .forEach(System.out::println);


        List<Document> documents = new ArrayList<>();
        for (Long key : topTen.keySet() ){
            documents.add(new Document(key,topTen.get(key)));
        }

        return  documents;
    }

    private Map<Long, Long> sortPopularity(Map<Long, Long> popularDocumentById) {
        Map<Long, Long> topTen =
                popularDocumentById.entrySet().stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                        .limit(10)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        return topTen;
    }

    private Map<Long, Long> calculatePopularity(List<DocumentOpenInfo> documentsInRange) {
        Map<Long, Long> popularDocumentById = new HashMap<>();
        for (DocumentOpenInfo openedDocument : documentsInRange){
            long documentId = openedDocument.getDocumentId();

            if (!popularDocumentById.containsKey(documentId)){
                popularDocumentById.put(documentId, 1L);
            } else {
                Long tempValue = popularDocumentById.get(documentId);
                popularDocumentById.put(documentId, tempValue + 1);
            }
        }
        return popularDocumentById;
    }


    public List<DocumentOpenInfo> getByDateRange() {

        LocalDate endDate = LocalDate.parse("2021-01-07");
        LocalDate startDate = LocalDate.parse("2021-01-01");
        return documentOpenInfoRepository.findAllByOpenDateIsBetween(startDate, endDate);
    }
}
