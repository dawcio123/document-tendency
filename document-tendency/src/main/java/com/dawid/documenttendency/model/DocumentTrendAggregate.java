package com.dawid.documenttendency.model;

import com.dawid.documenttendency.exception.DocumentError;
import com.dawid.documenttendency.exception.DocumentException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DocumentTrendAggregate {

    private List<DocumentTrendInfo> documentTrendInfoList;
    private int openingsSum;


    public int getOpeningsSum() {
        return openingsSum;
    }

    private final double OPENING_PERCENTILE = 5.0;
    public static final double TREND_MINIMAL_VALUE = 1.0;


    public void setDocumentTrendInfoList(List<DocumentTrendInfo> documentTrendInfoList) {
        this.documentTrendInfoList = documentTrendInfoList;
    }

    private int calculatePercentileIndex() {
        Collections.sort(documentTrendInfoList);
        return (int) Math.ceil(OPENING_PERCENTILE / 100.0 * documentTrendInfoList.size());

    }

    public List<DocumentTrendInfo> getListWithTrends() {
        this.openingsSum = calculateOpeningsSum(documentTrendInfoList);
        setTrendValue();
        int percintileIndex = calculatePercentileIndex();
        List<DocumentTrendInfo> documentTrendInfoListLimited = cutPercintile(this.documentTrendInfoList, percintileIndex);
        Collections.sort(documentTrendInfoListLimited, Collections.reverseOrder());
        return documentTrendInfoListLimited;

    }


    int calculateOpeningsSum(List<DocumentTrendInfo> documentTrendInfoList) {
        int openingsSum = 0;
        for (DocumentTrendInfo documentTrendInfo : documentTrendInfoList) {
            openingsSum += documentTrendInfo.getOpeningCount();
        }
        return openingsSum;
    }



    private List<DocumentTrendInfo> cutPercintile(List<DocumentTrendInfo> documentTrendInfoList, int percintileIndex) {
        List<DocumentTrendInfo> result = new ArrayList<>();

        Collections.sort(documentTrendInfoList, new OpeningComparator());


        for (int i = percintileIndex; i < documentTrendInfoList.size(); i++) {
            DocumentTrendInfo documentTrendInfo = documentTrendInfoList.get(i);
            if (documentTrendInfo.getTrendValue() >= TREND_MINIMAL_VALUE) {
                result.add(documentTrendInfoList.get(i));
            }

        }
        if (result.size() == 0) {
            throw new DocumentException(DocumentError.NO_DOCUMENTS_WITH_RAPID_TREND_FOUND);
        }
        return result;
    }

    private void setTrendValue() {
        for (DocumentTrendInfo documentTrendInfo : documentTrendInfoList) {
            documentTrendInfo.calculateTrend();
        }
    }


}
