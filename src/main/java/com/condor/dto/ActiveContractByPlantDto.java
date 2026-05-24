package com.condor.dto;

import lombok.Data;

@Data
public class ActiveContractByPlantDto {
  private String contractName;
  private Long contractTypeId;
  private Long plantId;
  private String plantName;
}
