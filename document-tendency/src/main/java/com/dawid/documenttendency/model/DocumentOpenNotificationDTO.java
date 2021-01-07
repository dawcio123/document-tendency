package com.dawid.documenttendency.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DocumentOpenNotificationDTO {



    private String documentId;
    private String userId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate openDate;

    public DocumentOpenNotificationDTO(String documentId, String userId, LocalDate localDate) {
        this.documentId = documentId;
        this.userId = userId;
        this.openDate = localDate;
    }
}
