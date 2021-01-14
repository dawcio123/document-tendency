package com.dawid.documenttendency.model.document;


public class DocumentTrendDto {

    private String documentId;
    private Double trendValue;


    public DocumentTrendDto(String documentId, Double trendValue) {
        this.documentId = documentId;
        this.trendValue = trendValue;
    }

    public String getDocumentId() {
        return documentId;
    }

    public Double getTrendValue() {
        return trendValue;
    }
}
