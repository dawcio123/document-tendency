package com.dawid.documenttendency.model.document;

import com.dawid.documenttendency.model.document.Document;

import java.util.List;

public interface TrendService {


    List<DocumentTrendDto> getTrendsForPreviousWeek();



    List<DocumentPopularDto> getPopularForPeriod(String fromDate, String toDate);

    List<DocumentTrendDto> getTrendsForPeriod(String fromDateString, String toDateString);
}
