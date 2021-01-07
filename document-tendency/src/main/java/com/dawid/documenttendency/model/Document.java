package com.dawid.documenttendency.model;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data

public class Document {

    private String id;
    private Long openCount;

    public Document(String id, Long openCount) {
        this.id = id;
        this.openCount = openCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Objects.equals(id, document.id) && Objects.equals(openCount, document.openCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, openCount);
    }
}
