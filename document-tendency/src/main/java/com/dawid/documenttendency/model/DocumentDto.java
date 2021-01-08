package com.dawid.documenttendency.model;

import lombok.Data;

import java.util.Objects;

@Data

public class DocumentDto {

    private String documentId;
    private Long openCount;

    public DocumentDto(String id, Long openCount) {
        this.documentId = id;
        this.openCount = openCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentDto document = (DocumentDto) o;
        return Objects.equals(documentId, document.documentId) && Objects.equals(openCount, document.openCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentId, openCount);
    }
}
