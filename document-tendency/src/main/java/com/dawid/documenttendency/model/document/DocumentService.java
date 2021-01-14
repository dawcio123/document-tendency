package com.dawid.documenttendency.model.document;

import com.dawid.documenttendency.model.document.Document;

import java.time.LocalDate;
import java.util.List;

public interface DocumentService {

    List<DocumentPopularDto> getPopularForPreviousWeek();

    List<DocumentTrendDto> getTrendsForPreviousWeek();


    List<DocumentPopularDto> getPopularForPeriod(LocalDate fromDate, LocalDate toDate);

    List<DocumentTrendDto> getTrendsForPeriod(LocalDate fromDate, LocalDate toDate);
}
