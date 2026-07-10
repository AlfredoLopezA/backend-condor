package com.condor.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ContractDocumentStatusDto {

    private Long documentId;
    private String documentNumber;
    private Long documentStatusId;
    private LocalDateTime documentDateIncome;
    private BigDecimal documentDirtyWeight;
    private Short documentCageDirty;
}