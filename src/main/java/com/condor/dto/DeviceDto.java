package com.condor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Device validation request/summary")
public class DeviceDto {
  @Schema(description = "Plant identifier", example = "1")
  private Short plantId;

  @Schema(description = "Role device id", example = "2")
  private Short roleDeviceId;

  @Schema(description = "Device hostname", example = "HOST01")
  private String deviceHostname;

  @Schema(description = "Device OS name", example = "Linux")
  private String deviceOsName;

  @Schema(description = "Validation result", example = "true")
  private Boolean valid;
}
