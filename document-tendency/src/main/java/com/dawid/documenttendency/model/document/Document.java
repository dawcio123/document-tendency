package com.dawid.documenttendency.model.document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
public class Document implements Comparable<Document> {

    private String documentId;
    @JsonIgnore
    private Map<LocalDate, Long> dateToOpeningCount;
    private Double trendValue;
    private int OpeningCount;
    @JsonIgnore
    private SimpleRegression r = new SimpleRegression(true);

    public Document(String documentId, Map<LocalDate, Long> opensAtDate) {
        this.documentId = documentId;
        this.dateToOpeningCount = opensAtDate;
    }

    public Document(String documentId) {
        this.documentId = documentId;
        this.dateToOpeningCount = new HashMap<>();

    }

    public void addOpenDate(LocalDate openDate){
        if (!dateToOpeningCount.containsKey(openDate)){
            dateToOpeningCount.put(openDate, 1L);
        } else {
            Long currentOpeningCount = dateToOpeningCount.get(openDate);
            dateToOpeningCount.put(openDate, currentOpeningCount +1);
        }
        OpeningCount ++;

    }

    public void calculateTrend(){

        Map<Double, Double> dateToOpeningCount = createDateToOpeningCount(this.dateToOpeningCount);
        addData(dateToOpeningCount);

        trendValue = r.getSlope();
    }

    private Map<Double, Double> createDateToOpeningCount(Map<LocalDate, Long> input){
        Map<Double, Double> dateToOpeningCount = new HashMap<>();

        for (Map.Entry<LocalDate, Long> entry : input.entrySet()){
            Double dateAsNumber = Double.valueOf(entry.getKey().toEpochDay());
            Double openingCount = Double.valueOf(entry.getValue());
            dateToOpeningCount.put(dateAsNumber, openingCount);
        }
        return dateToOpeningCount;

    }
    private void addData(Map<Double, Double> dateToOpeningCount){
        for (Map.Entry<Double, Double> entry : dateToOpeningCount.entrySet()){
            r.addData(entry.getKey(), entry.getValue());
        }
    }


    @Override
    public int compareTo(Document o){
        return this.getTrendValue().compareTo(o.getTrendValue());
    }
}
