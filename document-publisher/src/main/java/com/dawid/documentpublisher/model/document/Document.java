package com.dawid.documentpublisher.model.document;


import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Document {

    private String id;
    private String name;
    private String body;



}


