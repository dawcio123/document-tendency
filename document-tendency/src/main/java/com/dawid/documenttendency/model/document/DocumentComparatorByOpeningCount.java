package com.dawid.documenttendency.model.document;

import java.util.Comparator;

public class DocumentComparatorByOpeningCount implements Comparator<Document> {
    @Override
    public int compare(Document o1, Document o2) {
        return o1.getOpeningCount() - o2.getOpeningCount();
    }
}
