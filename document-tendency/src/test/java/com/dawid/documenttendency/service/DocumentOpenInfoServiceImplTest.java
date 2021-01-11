package com.dawid.documenttendency.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DocumentOpenInfoServiceImplTest {
    @Autowired
    TrendServiceImpl trendService;


    @Test
    void shouldSortPopularity() {

        Map<String, Long> testMap = new LinkedHashMap<>();

        testMap.put("last",1L);
        testMap.put("first",4L);
        testMap.put("sec",2L);


        List<String> notSortedTest = convertToKeyList(testMap);
        Integer resultsLimit = 10;
        Map<String, Long> result = trendService.sortPopularity(testMap, resultsLimit);


        List<String> sortedIdTest = convertToKeyList(result);

        Map<String, Long> resultMap = new LinkedHashMap<>();
        resultMap.put("first",4L);
        resultMap.put("sec",2L);
        resultMap.put("last",1L);

        List<String> sortedId = convertToKeyList(resultMap);

        assertTrue(sortedId.equals(sortedIdTest));
        assertFalse(sortedId.equals(notSortedTest));


    }

    private List<String> convertToKeyList(Map<String, Long> resultMap) {
        List<String> sortedId = new ArrayList<>();
        for (String id : resultMap.keySet()) {
            sortedId.add(id);
        }
        return sortedId;
    }
}