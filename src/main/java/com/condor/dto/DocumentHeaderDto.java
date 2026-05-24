package com.condor.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DocumentHeaderDto {
  private Long documentId;
  private String documentNumber;
  private Long contractId;
  private String contractName;
  private Short contractTypeId;
  private String contractTypeDescription;
  private Short plantId;
  private String plantName;
  private Short documentStatusId;
  private String documentStatusDescription;
  private LocalDateTime documentDateCreated;
  private LocalDateTime documentDateIncome;
  private LocalDateTime documentDateExit;
  private BigDecimal documentDirtyWeight;
  private BigDecimal documentCleanWeight;
  private Short documentCageDirty;
  private Short documentBulkDirty;
  private Short documentCageClean;
  private Short documentBulkClean;
  private Integer documentEpcCount;
  private Integer documentEpcOk;
  private Integer documentEpcNr;
}
