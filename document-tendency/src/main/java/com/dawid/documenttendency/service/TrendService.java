package com.dawid.documenttendency.service;

import com.dawid.documenttendency.model.DocumentDto;
import com.dawid.documenttendency.model.DocumentTrend;

import java.util.List;

public interface TrendService {



     List<DocumentDto> getPopular();

     List<DocumentTrend> getTrends();
}
