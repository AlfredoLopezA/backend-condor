package com.condor.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Station login response")
public class StationLoginResponseDto {

    @Schema(
        description = "JWT access token"
    )
    private String token;

    @Schema(
        description = "Token expiration time in seconds",
        example = "900"
    )
    private Long expiresIn;

    @Schema(
        description = "Plant identifier",
        example = "1"
    )
    private Short plantId;

    @Schema(
        description = "Device role identifier",
        example = "2"
    )
    private Short roleDeviceId;

    @Schema(
        description = "Device hostname",
        example = "PC-LAVADO-01"
    )
    private String hostname;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

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
}