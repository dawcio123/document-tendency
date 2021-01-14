package com.dawid.documenttendency.model.document;

import lombok.Data;


public class DocumentPopularDto {

    private String documentId;
    private int OpeningCount;


    public DocumentPopularDto(String documentId, int openingCount) {
        this.documentId = documentId;
        OpeningCount = openingCount;
    }

    public String getDocumentId() {
        return documentId;
    }

    public int getOpeningCount() {
        return OpeningCount;
    }
}
