package com.condor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Contract basic information")
public class ContractDto {
    @Schema(
        description = "Unique contract identifier",
        example = "1"
    )
    private Long contractId;
    @Schema(
        description = "Name contract",
        example = "Contract laundry"
    )
    private String contractName;
    @Schema(
        description = "Unique customer identifier",
        example = "10"
    )
    private Long customerId;
}