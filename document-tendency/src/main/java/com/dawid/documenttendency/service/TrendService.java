package com.dawid.documenttendency.service;

import com.dawid.documenttendency.model.DocumentDto;
import com.dawid.documenttendency.model.DocumentTrendInfo;

import java.util.List;

public interface TrendService {



     List<DocumentDto> getPopular(Integer resultLimit);



     List<DocumentTrendInfo> getTrendsForPreviousWeek();

    List<DocumentTrendInfo> getTrendsForPreviousWeek2();
}
