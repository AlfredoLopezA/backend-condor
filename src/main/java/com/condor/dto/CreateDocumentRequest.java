package com.condor.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class CreateDocumentRequest {
  private Long contractId;
  private Long plantId;
  private Instant documentDateIncome;
}