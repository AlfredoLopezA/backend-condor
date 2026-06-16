package com.condor.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Dashboard Summary")
public class DocumentDetailDto {
    @Schema(
        description = "Unique number detail identifier",
        example = "1"
    )
    private Long documentDetailId;
    @Schema(
        description = "Unique number identifier",
        example = "38"
    )
    private Long documentId;
    @Schema(
        description = "Unique tare identifier",
        example = "2"
    )
    private Long tareId;
    @Schema(
        description = "Tare weight",
        example = "42"
    )
    private BigDecimal tareWeight;
    @Schema(
        description = "Unique code identifier",
        example = "1"
    )
    private Short moveTypeId;
    @Schema(
        description = "Description typr of move",
        example = "Income"
    )
    private String moveTypeDescription;
    @Schema(
        description = "Clothes of weight",
        example = "48"
    )
    private BigDecimal documentDetailWeight;
    @Schema(
        description = "Total number of tags in cage or bulk",
        example = "78"
    )
    private Integer documentDetailEpcCount;
    @Schema(
        description = "Total number of tags in cage or bulk registered",
        example = "76"
    )
    private Integer documentDetailEpcOk;
    @Schema(
        description = "Total number of tags in cage or bulk no registered",
        example = "2"
    )
    private Integer documentDetailEpcNr;
    @Schema(
        description = "Time of add cage or bult in detail document",
        example = "10:10:10"
    )
    private LocalDateTime documentDetailTime;
}
