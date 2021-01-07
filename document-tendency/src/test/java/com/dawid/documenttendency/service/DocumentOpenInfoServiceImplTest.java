package com.dawid.documenttendency.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DocumentOpenInfoServiceImplTest {
    @Autowired
    DocumentOpenInfoServiceImpl documentOpenInfoService;

    DocumentOpenInfoServiceImpl documentOpenInfoService2;

    @Test
    void shouldSortPopularity() {

        Map<Long, Long> testMap = new LinkedHashMap<>();

        testMap.put(3L,1L);
        testMap.put(5L,4L);
        testMap.put(2L,2L);


        List<Long> notSortedTest = convertToKeyList(testMap);
        Map<Long, Long> result = documentOpenInfoService.sortPopularity(testMap);


        List<Long> sortedIdTest = convertToKeyList(result);

        Map<Long, Long> resultMap = new LinkedHashMap<>();
        resultMap.put(5L,4L);
        resultMap.put(2L,2L);
        resultMap.put(3L,1L);

        List<Long> sortedId = convertToKeyList(resultMap);

        assertTrue(sortedId.equals(sortedIdTest));
        assertFalse(sortedId.equals(notSortedTest));


    }

    private List<Long> convertToKeyList(Map<Long, Long> resultMap) {
        List<Long> sortedId = new ArrayList<>();
        for (Long id : resultMap.keySet()) {
            sortedId.add(id);
        }
        return sortedId;
    }
}