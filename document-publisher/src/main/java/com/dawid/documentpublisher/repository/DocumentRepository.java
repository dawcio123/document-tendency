package com.dawid.documentpublisher.repository;

import com.dawid.documentpublisher.model.document.Document;
import org.springframework.stereotype.Repository;

@Repository
public class DocumentRepository {

    public Document createDocument(long id){
        return Document.builder()
                .id(id)
                .name("doc name")
                .body("doc body")
                .build();
    }
}
