package com.condor.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Station or device validation request")
public class StationLoginRequestDto {

    @Schema(description = "Plant identifier", example = "1")
    private Short plantId;
    @Schema(description = "Station or device rol identifier", example = "2")
    private Short roleDeviceId;
    @Schema(description = "Station or device hostname", example = "COMPUTER-STATION")
    private String hostname;
    @Schema(description = "Station or device operative system", example = "LINUX FEDORA")
    private String osName;

    public Short getPlantId() {
        return plantId;
    }

    public void setPlantId(Short plantId) {
        this.plantId = plantId;
    }

    public Short getRoleDeviceId() {
        return roleDeviceId;
    }

    public void setRoleDeviceId(Short roleDeviceId) {
        this.roleDeviceId = roleDeviceId;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }
}