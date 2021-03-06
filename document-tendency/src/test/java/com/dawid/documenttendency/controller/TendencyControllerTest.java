package com.dawid.documenttendency.controller;


import com.dawid.documenttendency.model.document.*;
import com.dawid.documenttendency.util.Properties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TendencyControllerTest {

    @Container
    static PostgreSQLContainer database = new PostgreSQLContainer("postgres:12")
            .withDatabaseName("springboot")
            .withPassword("springboot")
            .withUsername("springboot");

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", database::getJdbcUrl);
        propertyRegistry.add("spring.datasource.password", database::getPassword);
        propertyRegistry.add("spring.datasource.username", database::getUsername);
    }


    @Autowired
    public TestRestTemplate testRestTemplate;



    @Test
    @DisplayName("JUNIOR - Should return popular document even is only one in db")
    @Sql("/scripts/INIT_DATA_ONLY_ONE_DOCUMENT.sql")
    void shouldReturnPopularDocumentIfIsOnlyOneInDb() throws Exception {


        ResponseEntity<DocumentPopularDto[]> result = testRestTemplate.getForEntity("/tendencies/popular", DocumentPopularDto[].class);
        List<DocumentPopularDto> resultList = Arrays.asList(result.getBody());

        assertEquals(1, resultList.size());
        assertEquals(7, resultList.get(0).getOpeningCount());
        assertEquals("b39280b4-5eed-4bf1-9555-62b5f4e18489", resultList.get(0).getDocumentId());


    }


    @Test
    @DisplayName("JUNIOR - Should return most popular document")
    @Sql("/scripts/INIT_DATA_MOST_POPULAR.sql")
    void shouldGetMostPopularDocumentFromLastWeek() throws Exception {


        ResponseEntity<DocumentPopularDto[]> result = testRestTemplate.getForEntity("/tendencies/popular", DocumentPopularDto[].class);
        List<DocumentPopularDto> resultList = Arrays.asList(result.getBody());


        assertEquals("b39280b4-5eed-4bf1-9555-62b5f4e18489", resultList.get(0).getDocumentId());

    }


    @Test
    @DisplayName("JUNIOR - Should return popular documents list in descending order")

    @Sql("/scripts/INIT_DATA_MOST_POPULAR.sql")
    void shouldReturnPopularDescending() throws Exception {


        ResponseEntity<DocumentPopularDto[]> result = testRestTemplate.getForEntity("/tendencies/popular", DocumentPopularDto[].class);
        List<DocumentPopularDto> resultList = Arrays.asList(result.getBody());


        assertEquals(14, resultList.get(0).getOpeningCount());
        assertNotEquals(13, resultList.get(0).getOpeningCount());
        assertEquals("b39280b4-5eed-4bf1-9555-62b5f4e18489", resultList.get(0).getDocumentId());

        assertEquals(7, resultList.get(1).getOpeningCount());
        assertNotEquals(6, resultList.get(1).getOpeningCount());
        assertEquals("Xb39280b4-5eed-4bf1-9555-62b5f4e18489", resultList.get(1).getDocumentId());

        assertEquals(4, resultList.get(2).getOpeningCount());
        assertNotEquals(3, resultList.get(2).getOpeningCount());
        assertEquals("Yb39280b4-5eed-4bf1-9555-62b5f4e18489", resultList.get(2).getDocumentId());

    }

    @Test
    @DisplayName("MID - Should return trending document with min set trend")
    @Sql("/scripts/INIT_DATA_FOR_TENDENCY_LAST_WEEK.sql")
    void shouldReturnTrendingDocument() throws Exception {


        ResponseEntity<DocumentTrendDto[]> result = testRestTemplate.getForEntity("/tendencies/trending", DocumentTrendDto[].class);
        List<DocumentTrendDto> resultList = Arrays.asList(result.getBody());


        assertEquals("b39280b4-5eed-4bf1-9555-62b5f4e18489", resultList.get(0).getDocumentId());
        assertTrue(resultList.get(0).getTrendValue() >= Properties.TREND_MINIMAL_VALUE);


    }

    @Test
    @DisplayName("MID - Should return list with trending documents sorted")
    @Sql("/scripts/INIT_DATA_FOR_TENDENCIES_FROM_LAST_WEEK.sql")
    void shouldReturnTrendingDocuments() throws Exception {


        ResponseEntity<DocumentTrendDto[]> result = testRestTemplate.getForEntity("/tendencies/trending", DocumentTrendDto[].class);
        List<DocumentTrendDto> resultList = Arrays.asList(result.getBody());

        double trendValue1 = resultList.get(0).getTrendValue();
        double trendValue2 = resultList.get(1).getTrendValue();
        double trendValue3 = resultList.get(2).getTrendValue();

        assertEquals(3, resultList.size());
        assertTrue(trendValue1 > trendValue2);
        assertTrue(trendValue2 > trendValue3);


    }

    @Test
    @DisplayName("SENIOR - Should return trending document with min set trend")
    @Sql("/scripts/INIT_DATA_FOR_TENDENCY_LAST_WEEK.sql")
    void shouldReturnTrendingDocumentForPeriod() throws Exception {


        ResponseEntity<DocumentTrendDto[]> result = testRestTemplate.getForEntity("/tendencies/trending/period?fromDate=2021-01-04&toDate=2021-01-10", DocumentTrendDto[].class);
        List<DocumentTrendDto> resultList = Arrays.asList(result.getBody());


        assertEquals("b39280b4-5eed-4bf1-9555-62b5f4e18489", resultList.get(0).getDocumentId());
        assertTrue(resultList.get(0).getTrendValue() >= DocumentServiceImpl.TREND_MINIMAL_VALUE);


    }

    @Test
    @DisplayName("SENIOR - Should return list with trending documents sorted")
    @Sql("/scripts/INIT_DATA_FOR_TENDENCIES_FROM_LAST_WEEK.sql")
    void shouldReturnTrendingDocumentsForPeriod() throws Exception {


        ResponseEntity<DocumentTrendDto[]> result = testRestTemplate.getForEntity("/tendencies/trending/period?fromDate=2021-01-11&toDate=2021-01-17", DocumentTrendDto[].class);
        List<DocumentTrendDto> resultList = Arrays.asList(result.getBody());

        double trendValue1 = resultList.get(0).getTrendValue();
        double trendValue2 = resultList.get(1).getTrendValue();
        double trendValue3 = resultList.get(2).getTrendValue();

        assertEquals(3, resultList.size());
        assertTrue(trendValue1 > trendValue2);
        assertTrue(trendValue2 > trendValue3);


    }

    @Test
    @DisplayName("SENIOR - Should return trending document with requested period")
    @Sql("/scripts/INIT_DATA_FOR_TENDENCY_DEFINED_PERIOD.sql")
    void shouldReturnTrendingDocumentForRequestedPeriod() throws Exception {


        ResponseEntity<DocumentTrendDto[]> result = testRestTemplate.getForEntity("/tendencies/trending/period?fromDate=2020-12-20&toDate=2021-01-10", DocumentTrendDto[].class);
        List<DocumentTrendDto> resultList = Arrays.asList(result.getBody());


        assertEquals("b39280b4-5eed-4bf1-9555-62b5f4e18489", resultList.get(0).getDocumentId());



    }

}