package com.dawid.documenttendency.model.openNotification;



import com.dawid.documenttendency.exception.DocumentException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import static com.dawid.documenttendency.exception.DocumentError.DOCUMENT_OPEN_INFO_NOT_FOUND;


@Service
public class DocumentOpenInfoServiceImpl implements DocumentOpenInfoService {

    private DocumentOpenInfoRepository documentOpenInfoRepository;


    public DocumentOpenInfoServiceImpl(DocumentOpenInfoRepository documentOpenInfoRepository) {
        this.documentOpenInfoRepository = documentOpenInfoRepository;

    }

    public void saveDocumentOpenInfo(DocumentOpenNotification documentOpenNotification) {
        DocumentOpenInfo documentOpenInfo = DocumentOpenInfo.builder()
                .documentId(documentOpenNotification.getDocumentId())
                .userId(documentOpenNotification.getUserId())
                .openDate(documentOpenNotification.getOpenDate())
                .build();

        documentOpenInfoRepository.save(documentOpenInfo);
    }


    public List<DocumentOpenInfo> getDocumentOpenInfoFromRange(LocalDate from, LocalDate toDate) {
        List<DocumentOpenInfo> documentOpenInfoFromRange = documentOpenInfoRepository.findAllByOpenDateIsBetween(from, toDate);

        if (documentOpenInfoFromRange.isEmpty()) {
            throw new DocumentException(DOCUMENT_OPEN_INFO_NOT_FOUND);
        }
        return documentOpenInfoFromRange;

    }

    public List<String> getDocumentsIds(LocalDate fromDate, LocalDate toDate) {

        List<DocumentOpenInfo> documentOpenInfoFromRange = getDocumentOpenInfoFromRange(fromDate, toDate);
        Set<String> documentIds = new HashSet<>();

        for (DocumentOpenInfo documentOpenInfo: documentOpenInfoFromRange){
            documentIds.add(documentOpenInfo.getDocumentId());
        }



            return new ArrayList<>(documentIds);
        }


}
