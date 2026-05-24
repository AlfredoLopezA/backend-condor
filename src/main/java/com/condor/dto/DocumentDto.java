package com.condor.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class DocumentDto {
  private Long documentId;
  private String documentNumber;
  private Long contractId;
  private Long plantId;
  private Instant documentDateCreated;
}