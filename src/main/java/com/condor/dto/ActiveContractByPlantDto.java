package com.condor.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Contracts information by plants")
public class ActiveContractByPlantDto {
    @Schema(
        description = "Name of contract",
        example = "Contract description"
    )
    private String contractName;
    @Schema(
        description = "Code of contract identifier",
        example = "1"
    )
    private Long contractTypeId;
    @Schema(
        description = "Code plant identifier",
        example = "1001"
    )
    private Long plantId;
    @Schema(
        description = "Name plant identifier",
        example = "Laundry Plant"
    )
    private String plantName;
}
