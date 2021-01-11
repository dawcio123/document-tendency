package com.dawid.documenttendency.exception;

public enum DocumentError {

    DOCUMENT_OPEN_INFO_NOT_FOUND("Not found data for requested period ");



    private String message;

    DocumentError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
