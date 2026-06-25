package com.condor.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Document header information")
public class DocumentHeaderDto {
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
        description = "Unique contract number",
        example = "800"
    )
    private Long contractId;
    @Schema(
        description = "Name or description of contract",
        example = "Client Fabric"
    )
    private String contractName;
    @Schema(
        description = "Code type of contract",
        example = "1"
    )
    private Short contractTypeId;
    @Schema(
        description = "Description kind of contract",
        example = "Laundry service"
    )
    private String contractTypeDescription;
    @Schema(
        description = "Code plant of proccess",
        example = "1"
    )
    private Short plantId;
    @Schema(
        description = "Name of plant",
        example = "Plant of Detroit"
    )
    private String plantName;

    @Schema(
        description = "Code of type document",
        example = "1"
    )
    private Short documenttypeId;
    @Schema(
        description = "Description of type",
        example = "Normal"
    )
    private String documenttypeDescription;
    @Schema(
        description = "Code status document",
        example = "1"
    )
    private Short documentStatusId;
    @Schema(
        description = "Description code status",
        example = "In proccess"
    )
    private String documentStatusDescription;
    @Schema(
        description = "Date of created document",
        example = "2026-01-01"
    )
    private LocalDateTime documentDateCreated;
    @Schema(
        description = "Date of income plant",
        example = "2026-01-01"
    )
    private LocalDateTime documentDateIncome;
    @Schema(
        description = "Date of delivery of customer",
        example = "2026-01-01"
    )
    private LocalDateTime documentDateExit;
    @Schema(
        description = "Dirty clothes weight in Kg",
        example = "200"
    )
    private BigDecimal documentDirtyWeight;
    @Schema(
        description = "Clean clothes weight in Kg",
        example = "180"
    )
    private BigDecimal documentCleanWeight;
    @Schema(
        description = "Number of dirty laundry cages",
        example = "20"
    )
    private Short documentCageDirty;
    @Schema(
        description = "Number of dirty laundry bulks",
        example = "5"
    )
    private Short documentBulkDirty;
    @Schema(
        description = "Number of clean laundry cages",
        example = "20"
    )
    private Short documentCageClean;
    @Schema(
        description = "Number of clean laundry bulks",
        example = "20"
    )
    private Short documentBulkClean;
    @Schema(
        description = "Number of total tags",
        example = "20"
    )
    private Integer documentEpcCount;
    @Schema(
        description = "Number of total registered tags",
        example = "20"
    )
    private Integer documentEpcOk;
    @Schema(
        description = "Number of total no registered tags",
        example = "20"
    )
    private Integer documentEpcNr;
}
