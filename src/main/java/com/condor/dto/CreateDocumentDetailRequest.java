package com.condor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Create document detail")
public class CreateDocumentDetailRequest {
    @Schema(
        description = "Tare identifier",
        example = "2"
    )
    private Long tareId;

    @Schema(
        description = "Net weight",
        example = "100.50"
    )
    private BigDecimal documentDetailWeight;
}