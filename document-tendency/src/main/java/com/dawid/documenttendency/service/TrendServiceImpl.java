package com.dawid.documenttendency.service;

import com.dawid.documenttendency.exception.DocumentException;
import com.dawid.documenttendency.model.DocumentDto;
import com.dawid.documenttendency.model.DocumentOpenInfo;
import com.dawid.documenttendency.model.DocumentTrendAggregate;
import com.dawid.documenttendency.model.DocumentTrendInfo;
import com.dawid.documenttendency.repository.DocumentOpenInfoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.dawid.documenttendency.exception.DocumentError.DOCUMENT_OPEN_INFO_NOT_FOUND;

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
        if (documentsInDateRange.size() == 0) {
            throw new NoSuchElementException();
        }
        Map<String, Long> documentPopularityById = calculatePopularity(documentsInDateRange);

        Map<String, Long> documentPopularityByIdSorted = sortPopularity(documentPopularityById, resultLimit);


        List<DocumentDto> documentsPopularity = generateDocuments(documentPopularityByIdSorted);

        return documentsPopularity;
    }



    public List<DocumentTrendInfo> getTrendsForPreviousWeek() {
        return getTrends(getFirstDayOfPreviousWeek());
    }


    private LocalDate getFirstDayOfPreviousWeek() {
        int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();
        LocalDate BegOfThisWeek = LocalDate.now().minusDays(dayOfWeek - 1);
        LocalDate fromDate = BegOfThisWeek.minusWeeks(1);
        return fromDate;
    }



    private List<DocumentTrendInfo> getTrends(LocalDate firstDayOfWeek) {


        LocalDate fromDate = firstDayOfWeek;
        LocalDate toDate = fromDate.plusDays(6);
        List<DocumentOpenInfo> documentsInDateRange = getDocumentOpenInfos(fromDate, toDate);

        List<DocumentTrendInfo> documentTrends = generateDocumentTrendInfos(documentsInDateRange);

        DocumentTrendAggregate documentTrendAggregate = new DocumentTrendAggregate();
        documentTrendAggregate.setDocumentTrendInfoList(documentTrends);

        return documentTrendAggregate.getListWithTrends();


    }

    private List<DocumentOpenInfo> getDocumentOpenInfos(LocalDate fromDate, LocalDate toDate) {
        List<DocumentOpenInfo> documentOpenInfoFromRange = documentOpenInfoService.getDocumentOpenInfoFromRange(fromDate, toDate);
        if (documentOpenInfoFromRange.size() == 0){
            throw new DocumentException(DOCUMENT_OPEN_INFO_NOT_FOUND);
        }

        return documentOpenInfoFromRange;

    }





    private List<DocumentTrendInfo> generateDocumentTrendInfos(List<DocumentOpenInfo> documentsInDateRange) {
        Map<String, DocumentTrendInfo> documentsWithCountedOpenings = new HashMap<>();

        for (DocumentOpenInfo openedDocument : documentsInDateRange) {
            String documentId = openedDocument.getDocumentId();

            if (!documentsWithCountedOpenings.containsKey(documentId)) {
                DocumentTrendInfo documentTrend = new DocumentTrendInfo(documentId, new TreeMap<LocalDate, Long>());
                documentsWithCountedOpenings.put(documentId, documentTrend);
            }
            DocumentTrendInfo documentTrend = documentsWithCountedOpenings.get(documentId);
            documentTrend.addOpenDate(openedDocument.getOpenDate());

        }
        List<DocumentTrendInfo> documentTrends = getDocumentTrendInfoList(documentsWithCountedOpenings);
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
        Map<String, Long> documentPopularityById = new HashMap<>();

        for (DocumentOpenInfo openedDocument : documentsInDateRange) {
            String documentId = openedDocument.getDocumentId();

            if (!documentPopularityById.containsKey(documentId)) {
                documentPopularityById.put(documentId, 1L);
            } else {
                Long currentOpeningCount = documentPopularityById.get(documentId);
                documentPopularityById.put(documentId, currentOpeningCount + 1);
            }
        }
        return documentPopularityById;
    }


    private List<DocumentDto> generateDocuments(Map<String, Long> documentsAndPopularity) {
        List<DocumentDto> documents = new ArrayList<>();
        for (String key : documentsAndPopularity.keySet()) {
            documents.add(new DocumentDto(key, documentsAndPopularity.get(key)));
        }
        return documents;
    }

    public Map<String, Long> sortPopularity(Map<String, Long> popularDocumentById, int limit) {
        Map<String, Long> sortedAndLimited =
                popularDocumentById.entrySet().stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                        .limit(limit)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        return sortedAndLimited;
    }


}
