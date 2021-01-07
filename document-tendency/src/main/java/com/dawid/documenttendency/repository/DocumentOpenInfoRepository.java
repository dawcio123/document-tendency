package com.dawid.documenttendency.repository;

import com.dawid.documenttendency.model.DocumentOpenInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DocumentOpenInfoRepository extends JpaRepository<DocumentOpenInfo, Long> {

    List<DocumentOpenInfo> findAllByOpenDateIsBetween(LocalDate start, LocalDate end);
}
