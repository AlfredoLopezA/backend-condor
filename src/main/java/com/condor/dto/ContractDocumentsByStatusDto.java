package com.condor.dto;

import lombok.Data;
import java.time.LocalDate;
import java.math.BigDecimal;

@Data
public class ContractDocumentsByStatusDto {
    private Long contractId;
    private String contractName;
    private Short contractTypeId;

    private Long documentId;
    private String documentNumber;
    private Short documentStatusId;
    private LocalDate documentDateIncome;
    private BigDecimal documentDirtyWeight;
    private Integer documentCageDirty;
}
