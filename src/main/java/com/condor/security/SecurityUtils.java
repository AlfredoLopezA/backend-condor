package com.condor.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static StationPrincipal getStation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (StationPrincipal) authentication.getPrincipal();
    }

    public static Long getDeviceId() {
        return getStation().getDeviceId();
    }

    public static Short getPlantId() {
        return getStation().getPlantId();
    }

    public static Short getRoleDeviceId() {
        return getStation().getRoleDeviceId();
    }

    public static String getHostname() {
        return getStation().getHostname();
    }

    public static String getOsName() {
        return getStation().getOsName();
    }

}