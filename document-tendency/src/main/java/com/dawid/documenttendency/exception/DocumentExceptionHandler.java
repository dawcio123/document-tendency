package com.dawid.documenttendency.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DocumentExceptionHandler {

    @ExceptionHandler(DocumentException.class)
    public ResponseEntity<ErrorInfo> handleException(DocumentException e){
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (DocumentError.DOCUMENT_OPEN_INFO_NOT_FOUND.equals(e.getDocumentError()) ||
                DocumentError.NO_DOCUMENTS_WITH_RAPID_TREND_FOUND.equals(e.getDocumentError())) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (DocumentError.DATE_HAS_NO_VALID_FORMAT.equals(e.getDocumentError()) ||
                DocumentError.DATE_END_IS_BEFORE_DATE_START.equals(e.getDocumentError())){
                httpStatus = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(httpStatus).body(new ErrorInfo(e.getDocumentError().getMessage()));
    }


}
