package com.dawid.documenttendency.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DocumentOpenNotificationDTO {



    private long documentId;
    private long userId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate openDate;

    public DocumentOpenNotificationDTO(long documentId, long userId, LocalDate localDate) {
        this.documentId = documentId;
        this.userId = userId;
        this.openDate = localDate;
    }
}
