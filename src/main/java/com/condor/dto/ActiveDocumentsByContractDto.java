package com.condor.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ActiveDocumentsByContractDto {
  private Long documentId;
  private String documentNumber;
  private LocalDateTime documentDateIncome;
  private BigDecimal documentDirtyWeight;
  private Short documentCageDirty;
  private Long documentStatusId;
}
