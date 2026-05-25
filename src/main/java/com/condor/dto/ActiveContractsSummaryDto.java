package com.condor.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Actives Contracts Summary")
public class ActiveContractsSummaryDto {
  @Schema(
          description = "Code contract identifier",
          example = "1"
  )
  private Long contractId;
  @Schema(
          description = "Name of contract",
          example = "Contract description"
  )
  private String contractName;
  @Schema(
          description = "Code active document",
          example = "1"
  )
  private Long activeDocuments;
  @Schema(
          description = "Proccess of document",
          example = "1"
  )
  private Long processingDocuments;
}
