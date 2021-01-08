package com.dawid.documenttendency.service;

import com.dawid.documenttendency.model.DocumentDto;
import com.dawid.documenttendency.model.DocumentOpenInfo;
import com.dawid.documenttendency.model.DocumentTrend;
import com.dawid.documenttendency.repository.DocumentOpenInfoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TrendServiceImpl implements TrendService {

    private DocumentOpenInfoRepository documentOpenInfoRepository;
    private DocumentOpenInfoService documentOpenInfoService;

    public TrendServiceImpl(DocumentOpenInfoRepository documentOpenInfoRepository, DocumentOpenInfoService documentOpenInfoService) {
        this.documentOpenInfoRepository = documentOpenInfoRepository;
        this.documentOpenInfoService = documentOpenInfoService;
    }




    public List<DocumentDto> getPopular() {

        LocalDate fromDate = getBeginningOfPreviousWeek();
        LocalDate toDate = fromDate.plusDays(6);
        List<DocumentOpenInfo> documentsInDateRange = documentOpenInfoService.getDocumentOpenInfoFromRange(fromDate, toDate);

        Map<String, Long> documentPopularityById = calculatePopularity(documentsInDateRange);
        Map<String, Long> documentPopularityByIdSorted = sortPopularity(documentPopularityById);


        List<DocumentDto> documentsPopularity = generateDocuments(documentPopularityByIdSorted);

        return  documentsPopularity;
    }

    private LocalDate getBeginningOfPreviousWeek(){
        int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();
        LocalDate BegOfThisWeek = LocalDate.now().minusDays(dayOfWeek-1);
        LocalDate fromDate = BegOfThisWeek.minusWeeks(1);
        return fromDate;
    }

    public List<DocumentTrend> getTrends() {


        LocalDate fromDate = getBeginningOfPreviousWeek();
        LocalDate toDate = fromDate.plusDays(6);
        List<DocumentOpenInfo> documentsInDateRange = documentOpenInfoService.getDocumentOpenInfoFromRange(fromDate, toDate);

        Map<String, DocumentTrend> documentsWithCountedOpenings = CountOpeningsForEachDocument(documentsInDateRange);

        List<DocumentTrend> documentTrends = new ArrayList<>();

        for(String documentId : documentsWithCountedOpenings.keySet()){
            documentTrends.add(documentsWithCountedOpenings.get(documentId));
        }

        for (DocumentTrend document : documentTrends){
            document.calculateTrend();
        }
        Collections.sort(documentTrends, Collections.reverseOrder());

        return documentTrends;

    }

    private Map<String, DocumentTrend> CountOpeningsForEachDocument(List<DocumentOpenInfo> documentsInDateRange) {
        Map<String, DocumentTrend> documentsWithCountedOpenings = new HashMap<>();

        for (DocumentOpenInfo openedDocument : documentsInDateRange){
            String documentId = openedDocument.getDocumentId();

            if (!documentsWithCountedOpenings.containsKey(documentId)){
                DocumentTrend documentTrend = new DocumentTrend(documentId, new TreeMap<LocalDate, Long>());
                documentsWithCountedOpenings.put(documentId, documentTrend);
            }
                DocumentTrend documentTrend = documentsWithCountedOpenings.get(documentId);
                documentTrend.addOpenDate(openedDocument.getOpenDate());

        }

        return documentsWithCountedOpenings;
    }


    private Map<String, Long> calculatePopularity(List<DocumentOpenInfo> documentsInDateRange) {
        Map<String, Long> documentPopularityById = new HashMap<>();

        for (DocumentOpenInfo openedDocument : documentsInDateRange){
            String documentId = openedDocument.getDocumentId();

            if (!documentPopularityById.containsKey(documentId)){
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
        for (String key : documentsAndPopularity.keySet() ){
            documents.add(new DocumentDto(key, documentsAndPopularity.get(key)));
        }
        return documents;
    }

    public Map<String, Long> sortPopularity(Map<String, Long> popularDocumentById) {
        Map<String, Long> topTen =
                popularDocumentById.entrySet().stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                        .limit(10)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        return topTen;
    }




    public List<DocumentOpenInfo> getByDateRange() {

        LocalDate endDate = LocalDate.parse("2021-01-07");
        LocalDate startDate = LocalDate.parse("2021-01-01");
        return documentOpenInfoRepository.findAllByOpenDateIsBetween(startDate, endDate);
    }
}
