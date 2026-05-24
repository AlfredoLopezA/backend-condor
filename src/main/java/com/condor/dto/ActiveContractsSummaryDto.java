package com.condor.dto;

import lombok.Data;

@Data
public class ActiveContractsSummaryDto {
  private Long contractId;
  private String contractName;
  private Long activeDocuments;
  private Long processingDocuments;
}
