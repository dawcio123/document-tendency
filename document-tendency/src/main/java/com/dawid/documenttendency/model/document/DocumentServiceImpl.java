package com.dawid.documenttendency.model.document;

import com.dawid.documenttendency.model.openNotification.DocumentOpenInfo;
import com.dawid.documenttendency.model.openNotification.DocumentOpenInfoService;
import com.dawid.documenttendency.util.DateService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class DocumentServiceImpl implements DocumentService {


    private DocumentOpenInfoService documentOpenInfoService;
    private DateService dateService;
    public static final double TREND_MINIMAL_VALUE = 1.0;

    public DocumentServiceImpl(DocumentOpenInfoService documentOpenInfoService, DateService dateService) {

        this.documentOpenInfoService = documentOpenInfoService;
        this.dateService = dateService;
    }

    public List<DocumentPopularDto> getPopularForPreviousWeek() {

        LocalDate fromDate = dateService.getFirstDayOfPreviousWeek();
        LocalDate toDate = fromDate.plusDays(6);
        return getPopularForPeriod(fromDate, toDate);
    }

    public List<DocumentPopularDto> getPopularForPeriod(LocalDate fromDate, LocalDate toDate) {


        dateService.validatePeriod(fromDate, toDate);
        List<Document> documents = getDocuments(fromDate, toDate);
        return getDocumentPopularDtos(documents);
    }

    public List<DocumentTrendDto> getTrendsForPreviousWeek() {

        LocalDate fromDate = dateService.getFirstDayOfPreviousWeek();
        LocalDate toDate = fromDate.plusDays(6);
        return getTrendsForPeriod(fromDate, toDate);
    }

    public List<DocumentTrendDto> getTrendsForPeriod(LocalDate fromDate, LocalDate toDate) {


        dateService.validatePeriod(fromDate, toDate);
        List<Document> documents = getDocuments(fromDate, toDate);
        return getDocumentTrendDtos(documents);
    }


    private List<DocumentPopularDto> getDocumentPopularDtos(List<Document> documents) {
        List<DocumentPopularDto> result = new ArrayList<>();

        documents.sort(new DocumentComparatorByOpeningCount().reversed());

        for (Document document : documents)
            result.add(document.createDocumentPopularDto());
        return result;
    }


    private List<DocumentTrendDto> getDocumentTrendDtos(List<Document> documents) {
        List<DocumentTrendDto> result = new ArrayList<>();


        Collections.sort(documents, Collections.reverseOrder());

        for (Document document : documents)

            if (hasDocumentTrendAboveThreshold(document)) {
                result.add(document.createDocumentTrendDto());
            }

        return result;
    }

    private boolean hasDocumentTrendAboveThreshold(Document document) {
        if (document.getTrendValue() >= TREND_MINIMAL_VALUE) {
            return true;
        } else return false;
    }


    private List<Document> getDocuments(LocalDate fromDate, LocalDate toDate) {

        List<String> documentsIds = documentOpenInfoService.getDocumentsIds(fromDate, toDate);
        DocumentRepository documentRepository = new DocumentRepository(documentsIds);

        List<DocumentOpenInfo> documentOpenInfos = getDocumentOpenInfos(fromDate, toDate);
        documentRepository.addOpeningToDocuments(documentOpenInfos);

        return documentRepository.getDocuments();

    }


    private List<DocumentOpenInfo> getDocumentOpenInfos(LocalDate fromDate, LocalDate toDate) {
        List<DocumentOpenInfo> documentOpenInfoFromRange = documentOpenInfoService.getDocumentOpenInfoFromRange(fromDate, toDate);

        return documentOpenInfoFromRange;

    }


}
