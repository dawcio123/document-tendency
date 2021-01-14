package com.dawid.documenttendency.service;

import com.dawid.documenttendency.model.DocumentDto;
import com.dawid.documenttendency.model.Document;

import java.util.List;

public interface TrendService {


    List<Document> getTrendsForPreviousWeek();

    List<Document> getTrendsForPeriod(String fromDate, String toDate);
    List<Document> getPopularForPeriod(String fromDate, String toDate);
}
