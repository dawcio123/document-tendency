package com.dawid.documenttendency.model.document;

import com.dawid.documenttendency.exception.DocumentException;
import com.dawid.documenttendency.model.openNotification.DocumentOpenInfo;
import com.dawid.documenttendency.model.openNotification.DocumentOpenInfoService;
import com.dawid.documenttendency.model.openNotification.DocumentOpenInfoRepository;
import org.apache.commons.validator.GenericValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import static com.dawid.documenttendency.exception.DocumentError.*;

@Service
public class TrendServiceImpl implements TrendService {

    private DocumentOpenInfoRepository documentOpenInfoRepository;
    private DocumentOpenInfoService documentOpenInfoService;
    public static final double TREND_MINIMAL_VALUE = 1.0;

    public TrendServiceImpl(DocumentOpenInfoRepository documentOpenInfoRepository, DocumentOpenInfoService documentOpenInfoService) {
        this.documentOpenInfoRepository = documentOpenInfoRepository;
        this.documentOpenInfoService = documentOpenInfoService;

    }





    public List<DocumentTrendDto> getTrendsForPreviousWeek() {

        LocalDate fromDate = getFirstDayOfPreviousWeek();
        LocalDate toDate = fromDate.plusDays(6);

        List<Document> documents =  getDocuments(fromDate, toDate);
        return getDocumentTrendDtos(documents);
    }

    public List<DocumentTrendDto> getTrendsForPeriod(String fromDateString, String toDateString) {

        hasInputRightFormat(fromDateString, toDateString);

        LocalDate fromDate = LocalDate.parse(fromDateString);
        LocalDate toDate = LocalDate.parse(toDateString);

        isPeriodValid(fromDate, toDate);
        List<Document> documents =  getDocuments(fromDate, toDate);
        return getDocumentTrendDtos(documents);
    }



    public List<DocumentPopularDto> getPopularForPeriod(String fromDateString, String toDateString) {


        hasInputRightFormat(fromDateString, toDateString);

        LocalDate fromDate = LocalDate.parse(fromDateString);
        LocalDate toDate = LocalDate.parse(toDateString);


        isPeriodValid(fromDate, toDate);

        List<Document> documents =  getDocuments(fromDate, toDate);
        return getDocumentPopularDtos(documents);
    }

    private void hasInputRightFormat(String fromDateString, String toDateString) {
        if (!hasDateValidFormat(fromDateString) || !hasDateValidFormat(toDateString)) {
            throw new DocumentException(DATE_HAS_NO_VALID_FORMAT);
        }
    }

    private void isPeriodValid(LocalDate fromDate, LocalDate toDate) {
        if (toDate.isBefore(fromDate)){
            throw new DocumentException(DATE_END_IS_BEFORE_DATE_START);
        }
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




    private List<DocumentPopularDto> getDocumentPopularDtos(List<Document> documents){
        List<DocumentPopularDto> result = new ArrayList<>();

        documents.sort(new DocumentComparatorByOpeningCount().reversed());

        for (Document document : documents)
            result.add(document.createDocumentPopularDto());
        return result;
    }


    private List<DocumentTrendDto> getDocumentTrendDtos(List<Document> documents){
        List<DocumentTrendDto> result = new ArrayList<>();


        Collections.sort(documents, Collections.reverseOrder());

        for (Document document : documents)

            if (hasDocumentTrendAboveThreshold(document)){
                result.add(document.createDocumentTrendDto());
            }


        return result;
    }
     private boolean hasDocumentTrendAboveThreshold(Document document){
        if (document.getTrendValue() >= TREND_MINIMAL_VALUE){
            return true;
        } else  return false;
     }


    private List<Document> getDocuments(LocalDate fromDate, LocalDate toDate) {

        List<String> documentsIds = documentOpenInfoService.getDocumentsIds(fromDate, toDate );
        DocumentRepository documentRepository = new DocumentRepository(documentsIds);

        List<DocumentOpenInfo> documentOpenInfos = getDocumentOpenInfos(fromDate, toDate);
        documentRepository.addOpeningToDocuments(documentOpenInfos);

        return documentRepository.getDocuments();


    }



    private List<DocumentOpenInfo> getDocumentOpenInfos(LocalDate fromDate, LocalDate toDate) {
        List<DocumentOpenInfo> documentOpenInfoFromRange = documentOpenInfoService.getDocumentOpenInfoFromRange(fromDate, toDate);
        if (documentOpenInfoFromRange.size() == 0) {
            throw new DocumentException(DOCUMENT_OPEN_INFO_NOT_FOUND);
        }

        return documentOpenInfoFromRange;

    }








}
