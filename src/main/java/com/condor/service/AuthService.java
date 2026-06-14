package com.condor.service;

import com.condor.dto.StationLoginRequestDto;
import com.condor.repository.DeviceRepository;
import org.springframework.stereotype.Service;
import com.condor.security.JwtService;
import com.condor.dto.StationLoginResponseDto;

@Service
public class AuthService {

    private final DeviceRepository deviceRepository;

    private final JwtService jwtService;

    public AuthService(
            DeviceRepository deviceRepository,
            JwtService jwtService
    ) {
        this.deviceRepository = deviceRepository;
        this.jwtService = jwtService;
    }
    
    public StationLoginResponseDto authorizeStation(
            StationLoginRequestDto request
    ) {

        boolean authorized = deviceRepository.validateDevice(
                request.getPlantId(),
                request.getRoleDeviceId(),
                request.getHostname(),
                request.getOsName()
        );

        if (!authorized) {
            return null;
        }

        Long deviceId = deviceRepository.getDeviceId(
                request.getPlantId(),
                request.getRoleDeviceId(),
                request.getHostname(),
                request.getOsName()
        );

        String token = jwtService.generateToken(
                deviceId,
                request.getPlantId(),
                request.getRoleDeviceId(),
                request.getHostname(),
                request.getOsName()
        );

        StationLoginResponseDto response =
                new StationLoginResponseDto();

        response.setToken(token);
        response.setExpiresIn(
                jwtService.getExpirationTimeSeconds()
        );

        response.setPlantId(request.getPlantId());
        response.setRoleDeviceId(
                request.getRoleDeviceId()
        );
        response.setHostname(request.getHostname());

        return response;
    }

}