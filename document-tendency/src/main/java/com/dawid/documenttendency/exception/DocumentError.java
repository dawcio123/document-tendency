package com.dawid.documenttendency.exception;

public enum DocumentError {

    DOCUMENT_OPEN_INFO_NOT_FOUND("Not found data for requested period "),
    NO_DOCUMENTS_WITH_RAPID_TREND_FOUND("Found no documents with rapid trend"),
    DATE_HAS_NO_VALID_FORMAT("Date for query has no valid format"),
    DATE_END_IS_BEFORE_DATE_START("End day of requested period is before date start");


    private String message;

    DocumentError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
