package com.dawid.documenttendency.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
public class DocumentTrend implements Comparable<DocumentTrend> {

    private String documentId;
    private Map<LocalDate, Long> opensAtDate;
    private Double trendValue;
    @JsonIgnore
    private SimpleRegression r = new SimpleRegression(true);

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

    public void calculateTrend(){

        Map<Double, Double> dataToCalculateTrend = transformDataToDouble(opensAtDate);
        addData(dataToCalculateTrend);

        trendValue = r.getSlope();
    }

    private Map<Double, Double> transformDataToDouble(Map<LocalDate, Long> input){
        Map<Double, Double> transformedToDoubleMap = new HashMap<>();

        for (Map.Entry<LocalDate, Long> entry : input.entrySet()){
            Double dateAsNumber = Double.valueOf(entry.getKey().toEpochDay());
            Double openingCount = Double.valueOf(entry.getValue());
            transformedToDoubleMap.put(dateAsNumber, openingCount);
        }
        return transformedToDoubleMap;

    }
    private void addData(Map<Double, Double> data){
        for (Map.Entry<Double, Double> entry : data.entrySet()){
            r.addData(entry.getKey(), entry.getValue());
        }
    }

//    @Override
//    public int compareTo(DocumentTrend o) {
//        if (this.trendValue == o.getTrendValue()){
//            return 0;
//        } else if (this.trendValue > o.getTrendValue()){
//            return 1;
//        } else return 1;
//    }

    @Override
    public int compareTo(DocumentTrend o){
        return this.getTrendValue().compareTo(o.getTrendValue());
    }
}
