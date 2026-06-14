package com.condor.security;

public class StationPrincipal {

    private final Long deviceId;    
    private final Short plantId;
    private final Short roleDeviceId;
    private final String hostname;
    private final String osName;

    public StationPrincipal(
        Long deviceId,
        Short plantId,
        Short roleDeviceId,
        String hostname,
        String osName) {

        this.deviceId = deviceId;
        this.plantId = plantId;
        this.roleDeviceId = roleDeviceId;
        this.hostname = hostname;
        this.osName = osName;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public Short getPlantId() {
        return plantId;
    }

    public Short getRoleDeviceId() {
        return roleDeviceId;
    }

    public String getHostname() {
        return hostname;
    }

    public String getOsName() {
        return osName;
    }

}