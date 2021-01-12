package com.dawid.documenttendency.exception;

public enum DocumentError {

    DOCUMENT_OPEN_INFO_NOT_FOUND("Not found data for requested period "),
    NO_DOCUMENTS_WITH_RAPID_TREND_FOUND("Found no documents with rapid trend");



    private String message;

    DocumentError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
