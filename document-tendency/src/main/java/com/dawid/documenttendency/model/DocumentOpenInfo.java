package com.dawid.documenttendency.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentOpenInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long id;
    @NotNull
    private long documentId;

    @NotNull
    private long userId;
    @NotNull
    private LocalDate openDate;
}
