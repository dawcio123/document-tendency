package com.dawid.documenttendency.repository;

import com.dawid.documenttendency.model.DocumentOpenInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DocumentOpenInfoRepositoryTest {

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
    private DocumentOpenInfoRepository documentOpenInfoRepository;

    @Test
    void shouldReturnAllEntries() {


        documentOpenInfoRepository.save(DocumentOpenInfo.builder()
                .documentId("b39280b4-5eed-4bf1-9555-62b5f4e18489")
                .userId("5ee49edc-b0f3-498b-ad2c-36c8e38e3064")
                .openDate(LocalDate.now())
                .build());
        List<DocumentOpenInfo> documentOpenInfoList = documentOpenInfoRepository.findAll();

        assertEquals(1, documentOpenInfoList.size());
    }

    @Test
    @Sql("/scripts/INIT_FIVE_DOCUMENTS.sql")
    void shouldReturnFiveEntries(){

        List<DocumentOpenInfo> documentOpenInfoList = documentOpenInfoRepository.findAll();

        assertEquals(5, documentOpenInfoList.size());
        assertNotEquals(4,documentOpenInfoList.size());
    }


    public DocumentOpenInfo createDocumentOpenInfo(String documentId, String userId, LocalDate openDate){
        return DocumentOpenInfo.builder()
                .documentId(documentId)
                .userId(userId)
                .openDate(openDate)
                .build();
    }
}