package com.dawid.documenttendency.model.document;

import com.dawid.documenttendency.model.document.Document;

import java.util.List;

public interface TrendService {


    List<Document> getTrendsForPreviousWeek();

    List<Document> getTrendsForPeriod(String fromDate, String toDate);
    List<Document> getPopularForPeriod(String fromDate, String toDate);
}
