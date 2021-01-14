package com.dawid.documenttendency.model;

import java.util.Comparator;

public class DocumentOpeningComparatorByOpeningCount implements Comparator<Document> {
    @Override
    public int compare(Document o1, Document o2) {
        return o1.getOpeningCount() - o2.getOpeningCount();
    }
}
