package com.condor.dto;

import lombok.Data;
import java.time.Instant;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Document header information")
public class DocumentDto {
  @Schema(
          description = "Unique document identifier",
          example = "1001"
  )
  private Long documentId;
  @Schema(
          description = "Unique document number",
          example = "D260101000001"
  )
  private String documentNumber;
  @Schema(
          description = "Unique contract identifier",
          example = "800"
  )
  private Long contractId;
  @Schema(
          description = "Unique code identifier plant operation",
          example = "1"
  )
  private Long plantId;
  private Instant documentDateCreated;
}