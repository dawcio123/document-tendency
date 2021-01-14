package com.dawid.documenttendency.exception;

public enum DocumentError {

    DOCUMENT_OPEN_INFO_NOT_FOUND("Not found data for requested period "),
    NO_DOCUMENTS_WITH_RAPID_TREND_FOUND("Found no documents with rapid trend"),
    DATE_HAS_NO_VALID_FORMAT("Invalid date format"),
    DATE_END_IS_BEFORE_DATE_START("Invalid requested period: End date is before start date");


    private String message;

    DocumentError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
