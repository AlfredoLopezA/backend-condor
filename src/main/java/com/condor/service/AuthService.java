package com.condor.service;

import com.condor.dto.StationLoginRequestDto;
import com.condor.repository.AuditRepository;
import com.condor.repository.DeviceRepository;
import com.condor.security.JwtService;
import com.condor.dto.StationLoginResponseDto;
import com.condor.constants.AuditEvents;
import com.condor.constants.AuditEntities;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final DeviceRepository deviceRepository;
    private final JwtService jwtService;
    private final AuditRepository auditRepository;
    public AuthService(DeviceRepository deviceRepository, JwtService jwtService, AuditRepository auditRepository) {
        this.deviceRepository = deviceRepository;
        this.jwtService = jwtService;
        this.auditRepository = auditRepository;
    }    

    public StationLoginResponseDto authorizeStation(StationLoginRequestDto request) {
        boolean authorized = deviceRepository.validateDevice(request.getPlantId(), request.getRoleDeviceId(), request.getHostname(), request.getOsName());
        if (!authorized) {
            return null;
        }
        Long deviceId = deviceRepository.getDeviceId(request.getPlantId(), request.getRoleDeviceId(), request.getHostname(), request.getOsName());
        String token = jwtService.generateToken(deviceId, request.getPlantId(), request.getRoleDeviceId(), request.getHostname(), request.getOsName());
        StationLoginResponseDto response = new StationLoginResponseDto();
        response.setToken(token);
        response.setExpiresIn(
                jwtService.getExpirationTimeSeconds()
        );
        response.setPlantId(request.getPlantId());
        response.setRoleDeviceId(request.getRoleDeviceId());
        response.setHostname(request.getHostname());
        auditRepository.saveAudit(deviceId, AuditEvents.STATION_LOGIN, AuditEntities.DEVICE, deviceId, "Technical login successful");
        return response;
    }

}