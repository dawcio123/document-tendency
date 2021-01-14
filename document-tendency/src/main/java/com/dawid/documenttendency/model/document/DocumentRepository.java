package com.dawid.documenttendency.model.document;

import com.dawid.documenttendency.exception.DocumentError;
import com.dawid.documenttendency.exception.DocumentException;
import com.dawid.documenttendency.model.openNotification.DocumentOpenInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DocumentRepository {
    private List<String> documentsIds;
    private List<Document> documents = new ArrayList<>();
    private int openingsSum;
    private final double OPENING_PERCENTILE = 5.0;
    public static final double TREND_MINIMAL_VALUE = 1.0;

    public int getOpeningsSum() {
        return openingsSum;
    }

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

    public List<Document> getListWithTrends() {
        this.openingsSum = calculateOpeningsSum(documents);
        setTrendValueToEachDocument();
        List<Document> documentsWithTrendAboveSetValue = removeDocumentsWIthLowTrendValue(documents);
        List<Document> documentsWithOpeningAboveSetPercentile = removeDocumnetsBelowSetPercintile(documentsWithTrendAboveSetValue);
        Collections.sort(documentsWithOpeningAboveSetPercentile, Collections.reverseOrder());
        return documentsWithOpeningAboveSetPercentile;

    }

    public List<Document> getDocuments() {
        this.openingsSum = calculateOpeningsSum(documents);
        setTrendValueToEachDocument();

        List<Document> documentsWithOpeningAboveSetPercentile = removeDocumnetsBelowSetPercintile(documents);

        return documentsWithOpeningAboveSetPercentile;

    }


    private List<Document> removeDocumentsWIthLowTrendValue(List<Document> documents) {
        List<Document> result = new ArrayList<>();

        for (Document document : documents) {
            if (document.getTrendValue() >= TREND_MINIMAL_VALUE) {
                result.add(document);
            }
        }

        if (result.size() == 0) {
            throw new DocumentException(DocumentError.NO_DOCUMENTS_WITH_RAPID_TREND_FOUND);
        }

        return result;
    }

    public List<Document> getListWithPopular() {
        this.openingsSum = calculateOpeningsSum(documents);
        List<Document> documentsWithOpeningAboveSetPercentile = removeDocumnetsBelowSetPercintile(documents);

        documentsWithOpeningAboveSetPercentile.sort(new DocumentComparatorByOpeningCount().reversed());
        //  Collections.sort(documents, new DocumentOpeningComparatorByOpeningCount());
        return documentsWithOpeningAboveSetPercentile;

    }

    private List<Document> removeDocumnetsBelowSetPercintile(List<Document> documents) {
        int percentileIndex = calculatePercentileIndex(documents);
        return cutPercintile(documents, percentileIndex);
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
        return (int) Math.ceil(OPENING_PERCENTILE / 100.0 * documents.size());

    }


    private List<Document> cutPercintile(List<Document> documentList, int percintileIndex) {
        List<Document> result = new ArrayList<>();

        Collections.sort(documentList, new DocumentComparatorByOpeningCount());
        for (int i = percintileIndex; i < documentList.size(); i++) {
            result.add(documentList.get(i));
        }
        return result;
    }


}
