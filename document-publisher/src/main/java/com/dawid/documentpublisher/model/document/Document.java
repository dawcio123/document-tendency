package com.dawid.documentpublisher.model.document;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Document {

    private Long id;
    private String name;
    private String body;

}
