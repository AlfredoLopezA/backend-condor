package com.condor.dto;

import lombok.Data;
import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Dashboard Summary")
public class DashboardSummaryDto {
    @Schema(
        description = "Total document",
        example = "38"
    )
    private Short totalDocuments;
    @Schema(
        description = "Total documents active",
        example = "32"
    )
    private Short activeDocuments;
    @Schema(
        description = "Total documents closed",
        example = "6"
    )
    private Short closedDocuments;
    @Schema(
        description = "Total documents today",
        example = "27"
    )
    private Short todayDocuments;
    @Schema(
        description = "Total previuos document",
        example = "11"
    )
    private Short previousDocuments;
    @Schema(
        description = "Total dirty weight",
        example = "10000"
    )
    private BigDecimal totalDirtyWeight;
    @Schema(
        description = "Total clean weight",
        example = "8000"
    )
    private BigDecimal totalCleanWeight;
    @Schema(
        description = "% Compliance",
        example = "80 %"
    )
    private BigDecimal compliancePercentage;
    @Schema(
        description = "Total dirty cages",
        example = "250"
    )
    private Short totalDirtyCages;
    @Schema(
        description = "Total dirty bulks",
        example = "25"
    )
    private Short totalDirtyBulks;
    @Schema(
        description = "Total clean cages",
        example = "150"
    )
    private Short totalCleanCages;
    @Schema(
        description = "Total clean bulks",
        example = "10"
    )
    private Short totalCleanBulks;
}
