package com.dawid.documenttendency.model.document;

import com.dawid.documenttendency.model.openNotification.DocumentOpenInfo;
import com.dawid.documenttendency.util.Properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DocumentRepository {
    private List<String> documentsIds;
    private List<Document> documents;
    private int openingsSum;





    public DocumentRepository(List<String> documentsIds) {
        this.documentsIds = documentsIds;
        this.documents = generateDocuments(documentsIds);
    }

    private List<Document> generateDocuments(List<String> documentsIds) {
        List<Document> documents = new ArrayList<>();
        for (String documentId : documentsIds) {
            documents.add(new Document(documentId));
        }
        return documents;
    }

    public void addOpeningToDocuments(List<DocumentOpenInfo> documentOpenInfos) {
        //One list can be transformed to map if optimization needed

        for (DocumentOpenInfo documentOpenInfo : documentOpenInfos) {
            for (Document document : this.documents) {
                if (documentOpenInfo.getDocumentId().equals(document.getDocumentId())) {
                    document.addOpenDate(documentOpenInfo.getOpenDate());
                }
            }
        }
    }


    public List<Document> getDocuments() {
        this.openingsSum = calculateOpeningsSum(documents);
        setTrendValueToEachDocument();

        List<Document> documentsWithOpeningAboveSetPercentile = removeDocumnetsBelowSetPercintile(documents);

        return documentsWithOpeningAboveSetPercentile;

    }



    private List<Document> removeDocumnetsBelowSetPercintile(List<Document> documents) {
        int percentileIndex = calculatePercentileIndex(documents);
        return removeValuesBelowSetPercentile(documents, percentileIndex);
    }


    private int calculateOpeningsSum(List<Document> documents) {
        int openingsSum;
        openingsSum = 0;

        for (Document document : documents) {
            openingsSum += document.getOpeningCount();
        }
        return openingsSum;
    }

    private void setTrendValueToEachDocument() {
        for (Document document : documents) {
            document.calculateTrend();
        }
    }

    private int calculatePercentileIndex(List<Document> documents) {
        return (int) Math.ceil(Properties.OPENING_PERCENTILE / 100.0 * documents.size());

    }


    private List<Document> removeValuesBelowSetPercentile(List<Document> documentList, int percintileIndex) {

        if (documentList.size() == 1){
            return documentList;
        }

        List<Document> result = new ArrayList<>();

        Collections.sort(documentList, new DocumentComparatorByOpeningCount());
        int percintileValue = documentList.get(percintileIndex-1).getOpeningCount();
        for (int i = 0; i < documentList.size(); i++) {
            if (documentList.get(i).getOpeningCount() > percintileValue){
                result.add(documentList.get(i));
            }

        }
        return result;
    }


}
