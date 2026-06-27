package com.condor.dto;

import lombok.Data;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Document datails of RFID or tags")
public class DocumentRfidDetailDto {
    @Schema(
        description = "Unique identifier",
        example = "1"
    )
    private Long productUnitTransactionId;
    @Schema(
        description = "Id of detail document",
        example = "4"
    )
    private Long documentDetailId;
    @Schema(
        description = "Unique product number identifier",
        example = "120"
    )
    private Long productUnitId;
    @Schema(
        description = "Time of registered transaction",
        example = "12:10:38"
    )
    private LocalDateTime productUnitTransactionDatetime;
    @Schema(
        description = "Id transaction identifier",
        example = "1"
    )
    private Long productUnitTransactionTypeId;
    @Schema(
        description = "Valid transaction",
        example = "True"
    )
    private Boolean productUnitTransactionValid;
}
