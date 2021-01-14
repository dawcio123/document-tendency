package com.dawid.documenttendency.service;

import com.dawid.documenttendency.exception.DocumentException;
import com.dawid.documenttendency.model.DocumentDto;
import com.dawid.documenttendency.model.DocumentOpenInfo;
import com.dawid.documenttendency.model.DocumentTrendAggregate;
import com.dawid.documenttendency.model.Document;
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





    public List<Document> getTrendsForPreviousWeek() {

        LocalDate fromDate = getFirstDayOfPreviousWeek();
        LocalDate toDate = fromDate.plusDays(6);

        return getTrends(fromDate, toDate);
    }


    public List<Document> getPopularForPeriod(String fromDateString, String toDateString) {

        //validate input
        if (!hasDateValidFormat(fromDateString) || !hasDateValidFormat(toDateString)){
            throw new DocumentException(DATE_HAS_NO_VALID_FORMAT);
        }

        //parse
        LocalDate fromDate = LocalDate.parse(fromDateString);
        LocalDate toDate = LocalDate.parse(toDateString);

        //validate
        if (toDate.isBefore(fromDate)){
            throw new DocumentException(DATE_END_IS_BEFORE_DATE_START);
        }

        return getPopulars(fromDate, toDate);
    }


    public List<Document> getTrendsForPeriod(String fromDateString, String toDateString) {

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


    private List<Document> getTrends(LocalDate fromDate, LocalDate toDate) {

        List<String> documentsIds = documentOpenInfoService.getDocumentsIds(fromDate, toDate );
        DocumentTrendAggregate documentTrendAggregate = new DocumentTrendAggregate(documentsIds);

        List<DocumentOpenInfo> documentOpenInfos = getDocumentOpenInfos(fromDate, toDate);
        documentTrendAggregate.addOpeningToDocuments(documentOpenInfos);

        return documentTrendAggregate.getListWithTrends();


    }

    private List<Document> getPopulars(LocalDate fromDate, LocalDate toDate) {

        List<String> documentsIds = documentOpenInfoService.getDocumentsIds(fromDate, toDate );
        DocumentTrendAggregate documentTrendAggregate = new DocumentTrendAggregate(documentsIds);

        List<DocumentOpenInfo> documentOpenInfos = getDocumentOpenInfos(fromDate, toDate);
        documentTrendAggregate.addOpeningToDocuments(documentOpenInfos);

        return documentTrendAggregate.getListWithPopular();


    }

    private List<DocumentOpenInfo> getDocumentOpenInfos(LocalDate fromDate, LocalDate toDate) {
        List<DocumentOpenInfo> documentOpenInfoFromRange = documentOpenInfoService.getDocumentOpenInfoFromRange(fromDate, toDate);
        if (documentOpenInfoFromRange.size() == 0) {
            throw new DocumentException(DOCUMENT_OPEN_INFO_NOT_FOUND);
        }

        return documentOpenInfoFromRange;

    }








}
