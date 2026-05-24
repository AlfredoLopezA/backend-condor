package com.condor.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DocumentRfidDetailDto {
  private Long productUnitTransactionId;
  private Long documentDetailId;
  private Long productUnitId;
  private LocalDateTime productUnitTransactionDatetime;
  private Long productUnitTransactionTypeId;
  private Boolean productUnitTransactionValid;
}
