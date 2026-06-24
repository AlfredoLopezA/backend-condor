package com.condor.dto;

import lombok.Data;
import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "Create a new document")
public class CreateDocumentRequest {
    @Schema(
        description = "Unique contract identifier",
        example = "1"
    )
    private Long contractId;
    @Schema(
        description = "Date document income",
        example = "2026-01-01"
    )
    private LocalDate documentDateIncome;
    @NotNull(message = "Document type is required")
    private Short documentTypeId;
}