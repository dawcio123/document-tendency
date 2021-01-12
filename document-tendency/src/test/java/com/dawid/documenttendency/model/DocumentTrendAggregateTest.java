package com.dawid.documenttendency.model;

import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTrendAggregateTest {

    @Test
    void calculateOpeningsSum() {

        DocumentTrendAggregate documentTrendAggregate = new DocumentTrendAggregate();


        DocumentTrendInfo doc1 = new DocumentTrendInfo("id1", new TreeMap<LocalDate, Long>());
        DocumentTrendInfo doc2 = new DocumentTrendInfo("id2", new TreeMap<LocalDate, Long>());
        DocumentTrendInfo doc3 = new DocumentTrendInfo("id3", new TreeMap<LocalDate, Long>());

        doc1.addOpenDate(LocalDate.now());
        doc2.addOpenDate(LocalDate.now());
        doc3.addOpenDate(LocalDate.now());

        List<DocumentTrendInfo> documentTrendInfoList = new ArrayList<>();
        documentTrendInfoList.add(doc1);
        documentTrendInfoList.add(doc2);
        documentTrendInfoList.add(doc3);


        int result = documentTrendAggregate.calculateOpeningsSum(documentTrendInfoList);

        assertEquals(3, result);
    }



}