package com.condor.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "Actives Documents by Contracts")
public class ActiveDocumentsByContractDto {
  @Schema(
        description = "Unique document identifier",
        example = "1"
  )
  private Long documentId;
  @Schema(
        description = "Unique document number identifier",
        example = "20260101000001"
  )
  private String documentNumber;
  @Schema(
        description = "Unique document number identifier",
        example = "20260101000001"
  )
  private LocalDateTime documentDateIncome;
  @Schema(
        description = "Date document income",
        example = "2026-01-01"
  )
  private BigDecimal documentDirtyWeight;
  @Schema(
        description = "Number of dirty leundry cages",
        example = "20"
  )
  private Short documentCageDirty;
  @Schema(
        description = "Code status identifier",
        example = "1"
  )
  private Long documentStatusId;
}
