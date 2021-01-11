package com.dawid.documenttendency.exception;

public class DocumentException extends RuntimeException {


    private DocumentError documentError;

    public DocumentError getDocumentError() {
        return documentError;
    }

    public DocumentException(DocumentError documentError) {
        this.documentError = documentError;
    }
}
