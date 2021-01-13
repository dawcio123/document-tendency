package com.dawid.documenttendency.service;

import com.dawid.documenttendency.exception.DocumentException;
import com.dawid.documenttendency.model.DocumentDto;
import com.dawid.documenttendency.model.DocumentOpenInfo;
import com.dawid.documenttendency.model.DocumentTrendAggregate;
import com.dawid.documenttendency.model.DocumentTrendInfo;
import com.dawid.documenttendency.repository.DocumentOpenInfoRepository;
import org.apache.commons.validator.GenericValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.dawid.documenttendency.exception.DocumentError.*;

@Service
public class TrendServiceImpl implements TrendService {

    private DocumentOpenInfoRepository documentOpenInfoRepository;
    private DocumentOpenInfoService documentOpenInfoService;

    public TrendServiceImpl(DocumentOpenInfoRepository documentOpenInfoRepository, DocumentOpenInfoService documentOpenInfoService) {
        this.documentOpenInfoRepository = documentOpenInfoRepository;
        this.documentOpenInfoService = documentOpenInfoService;
    }


    public List<DocumentDto> getPopular(Integer resultLimit) {
        if (resultLimit == null) {
            resultLimit = 10;
        }


        LocalDate fromDate = getFirstDayOfPreviousWeek();
        LocalDate toDate = fromDate.plusDays(6);
        List<DocumentOpenInfo> documentsInDateRange = getDocumentOpenInfos(fromDate, toDate);


        Map<String, Long> documentIdToOpeningCount = calculatePopularity(documentsInDateRange);

        Map<String, Long> documentIdToOpeningCountSortedAndLimited = sortPopularity(documentIdToOpeningCount, resultLimit);


        List<DocumentDto> documentsPopularity = generateDocuments(documentIdToOpeningCountSortedAndLimited);

        return documentsPopularity;
    }


    public List<DocumentTrendInfo> getTrendsForPreviousWeek() {

        LocalDate fromDate = getFirstDayOfPreviousWeek();
        LocalDate toDate = fromDate.plusDays(6);

        return getTrends(fromDate, toDate);
    }

    @Override
    public List<DocumentTrendInfo> getTrendsForPeriod(String fromDateString, String toDateString) {

        if (!hasDateValidFormat(fromDateString) || !hasDateValidFormat(toDateString)){
            throw new DocumentException(DATE_HAS_NO_VALID_FORMAT);
        }

        LocalDate fromDate = LocalDate.parse(fromDateString);
        LocalDate toDate = LocalDate.parse(toDateString);

        if (toDate.isBefore(fromDate)){
            throw new DocumentException(DATE_END_IS_BEFORE_DATE_START);
        }

        return getTrends(fromDate, toDate);
    }

    private boolean hasDateValidFormat(String date) {
        String pattern = "yyyy-MM-dd";

        return(GenericValidator.isDate(date,pattern,true));
    }




    private LocalDate getFirstDayOfPreviousWeek() {
        int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();
        LocalDate BegOfThisWeek = LocalDate.now().minusDays(dayOfWeek - 1);
        LocalDate fromDate = BegOfThisWeek.minusWeeks(1);
        return fromDate;
    }


    private List<DocumentTrendInfo> getTrends(LocalDate fromDate, LocalDate toDate) {



        List<DocumentOpenInfo> documentsInDateRange = getDocumentOpenInfos(fromDate, toDate);

        List<DocumentTrendInfo> documentTrends = generateDocumentTrendInfos(documentsInDateRange);

        DocumentTrendAggregate documentTrendAggregate = new DocumentTrendAggregate();
        documentTrendAggregate.setDocumentTrendInfoList(documentTrends);

        return documentTrendAggregate.getListWithTrends();


    }

    private List<DocumentOpenInfo> getDocumentOpenInfos(LocalDate fromDate, LocalDate toDate) {
        List<DocumentOpenInfo> documentOpenInfoFromRange = documentOpenInfoService.getDocumentOpenInfoFromRange(fromDate, toDate);
        if (documentOpenInfoFromRange.size() == 0) {
            throw new DocumentException(DOCUMENT_OPEN_INFO_NOT_FOUND);
        }

        return documentOpenInfoFromRange;

    }


    private List<DocumentTrendInfo> generateDocumentTrendInfos(List<DocumentOpenInfo> documentsInDateRange) {
        Map<String, DocumentTrendInfo> documentIdToDocumentTrendInfo = new HashMap<>();

        for (DocumentOpenInfo openedDocument : documentsInDateRange) {
            String documentId = openedDocument.getDocumentId();

            if (!documentIdToDocumentTrendInfo.containsKey(documentId)) {
                DocumentTrendInfo documentTrend = new DocumentTrendInfo(documentId, new TreeMap<LocalDate, Long>());
                documentIdToDocumentTrendInfo.put(documentId, documentTrend);
            }
            DocumentTrendInfo documentTrend = documentIdToDocumentTrendInfo.get(documentId);
            documentTrend.addOpenDate(openedDocument.getOpenDate());

        }
        List<DocumentTrendInfo> documentTrends = getDocumentTrendInfoList(documentIdToDocumentTrendInfo);
        return documentTrends;
    }

    private List<DocumentTrendInfo> getDocumentTrendInfoList(Map<String, DocumentTrendInfo> documentsWithCountedOpenings) {
        List<DocumentTrendInfo> documentTrends = new ArrayList<>();

        for (String documentId : documentsWithCountedOpenings.keySet()) {
            documentTrends.add(documentsWithCountedOpenings.get(documentId));
        }
        return documentTrends;
    }


    private Map<String, Long> calculatePopularity(List<DocumentOpenInfo> documentsInDateRange) {
        Map<String, Long> documentIdToOpeningCount = new HashMap<>();

        for (DocumentOpenInfo openedDocument : documentsInDateRange) {
            String documentId = openedDocument.getDocumentId();

            if (!documentIdToOpeningCount.containsKey(documentId)) {
                documentIdToOpeningCount.put(documentId, 1L);
            } else {
                Long currentOpeningCount = documentIdToOpeningCount.get(documentId);
                documentIdToOpeningCount.put(documentId, currentOpeningCount + 1);
            }
        }
        return documentIdToOpeningCount;
    }


    private List<DocumentDto> generateDocuments(Map<String, Long> documentsAndPopularity) {
        List<DocumentDto> documents = new ArrayList<>();
        for (String key : documentsAndPopularity.keySet()) {
            documents.add(new DocumentDto(key, documentsAndPopularity.get(key)));
        }
        return documents;
    }

    public Map<String, Long> sortPopularity(Map<String, Long> documentIdToOpeningCount, int limit) {
        Map<String, Long> sortedAndLimited =
                documentIdToOpeningCount.entrySet().stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                        .limit(limit)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        return sortedAndLimited;
    }


}
