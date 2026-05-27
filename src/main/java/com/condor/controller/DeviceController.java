package com.condor.controller;

import com.condor.dto.DeviceDto;
import com.condor.service.DeviceService;
import com.condor.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/api/devices")
@Validated
@Tag(name = "Devices", description = "Device validation services")
public class DeviceController {

  private final DeviceService deviceService;

  public DeviceController(DeviceService deviceService) {
    this.deviceService = deviceService;
  }

  /**
   * Validate device using DB function. Returns ApiResponse with the validation result.
   * Example: GET /api/devices/validate?plantId=1&roleDeviceId=2&hostname=H1&osName=Linux
   */
  @GetMapping("/validate")
  public ResponseEntity<ApiResponse<DeviceDto>> validateDevice(
      @RequestParam(name = "plantId") Short plantId,
      @RequestParam(name = "roleDeviceId") Short roleDeviceId,
      @RequestParam(name = "hostname") String deviceHostname,
      @RequestParam(name = "osName") String deviceOsName) {

    boolean valid = deviceService.isValidDevice(plantId, roleDeviceId, deviceHostname, deviceOsName);

    DeviceDto dto = new DeviceDto();
    dto.setPlantId(plantId);
    dto.setRoleDeviceId(roleDeviceId);
    dto.setDeviceHostname(deviceHostname);
    dto.setDeviceOsName(deviceOsName);
    dto.setValid(valid);

    ApiResponse<DeviceDto> response = new ApiResponse<>(
        true,
        valid ? "DEVICE_VALIDATION_OK" : "DEVICE_VALIDATION_NOK",
        valid ? "DEVICE_VALID" : "DEVICE_INVALID",
        dto
    );

    return ResponseEntity.ok(response);
  }
}
