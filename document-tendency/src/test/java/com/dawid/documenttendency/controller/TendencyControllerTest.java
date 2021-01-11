package com.dawid.documenttendency.controller;


import com.dawid.documenttendency.model.DocumentDto;
import com.dawid.documenttendency.model.DocumentOpenInfo;

import com.dawid.documenttendency.repository.DocumentOpenInfoRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
    private TendencyController tendencyController;

    @Autowired
    public TestRestTemplate testRestTemplate;

    @Autowired
    private DocumentOpenInfoRepository documentOpenInfoRepository;

    @Test
    @Sql("/scripts/INIT_FIVE_DOCUMENTS.sql")
    void shouldReturnDocumentWithOpeningCount() throws Exception {
        List<DocumentOpenInfo> documentOpenInfoList = documentOpenInfoRepository.findAll();

        assertEquals(5, documentOpenInfoList.size());
        assertNotEquals(4, documentOpenInfoList.size());


        ResponseEntity<DocumentDto[]> result = testRestTemplate.getForEntity("/tendencies/popular", DocumentDto[].class);
        List<DocumentDto> resultList = Arrays.asList(result.getBody());

        assertEquals(1, resultList.size());
        assertEquals(5, resultList.get(0).getOpenCount());
        assertEquals("b39280b4-5eed-4bf1-9555-62b5f4e18489", resultList.get(0).getDocumentId());


        ResponseEntity<DocumentDto> result2 = testRestTemplate.getForEntity("/tendencies/pop", DocumentDto.class);

        assertEquals(5, result2.getBody().getOpenCount());


    }
}