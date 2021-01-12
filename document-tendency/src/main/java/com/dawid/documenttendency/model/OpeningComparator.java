package com.dawid.documenttendency.model;

import java.util.Comparator;

public class OpeningComparator implements Comparator<DocumentTrendInfo> {
    @Override
    public int compare(DocumentTrendInfo o1, DocumentTrendInfo o2) {
        return o1.getOpeningCount() - o2.getOpeningCount();
    }
}
