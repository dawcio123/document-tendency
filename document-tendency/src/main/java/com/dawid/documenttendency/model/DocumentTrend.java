package com.dawid.documenttendency.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
public class DocumentTrend {

    private String documentId;
    private Map<LocalDate, Long> opensAtDate;

    public DocumentTrend(String documentId, Map<LocalDate, Long> opensAtDate) {
        this.documentId = documentId;
        this.opensAtDate = opensAtDate;
    }

    public void addOpenDate(LocalDate openDate){
        if (!opensAtDate.containsKey(openDate)){
            opensAtDate.put(openDate, 1L);
        } else {
            Long currentOpeningCount = opensAtDate.get(openDate);
            opensAtDate.put(openDate, currentOpeningCount +1);
        }
    }
}
