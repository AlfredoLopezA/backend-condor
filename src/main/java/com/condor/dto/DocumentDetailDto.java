package com.condor.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DocumentDetailDto {
  private Long documentDetailId;
  private Long documentId;
  private Long tareId;
  private BigDecimal tareWeight;
  private Short moveTypeId;
  private String moveTypeDescription;
  private BigDecimal documentDetailWeight;
  private Integer documentDetailEpcCount;
  private Integer documentDetailEpcOk;
  private Integer documentDetailEpcNr;
  private LocalDateTime documentDetailTime;
}
