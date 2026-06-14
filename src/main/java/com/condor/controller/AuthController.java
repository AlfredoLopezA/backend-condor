package com.condor.controller;

import com.condor.dto.StationLoginRequestDto;
import com.condor.dto.response.ApiResponse;
import com.condor.security.SecurityUtils;
import com.condor.security.StationPrincipal;
import com.condor.service.AuditService;
import com.condor.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.condor.dto.StationLoginResponseDto;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService, AuditService auditService) {
        this.authService = authService;
        this.auditService = auditService;
    }

    @PostMapping("/station-login")
    public ResponseEntity<ApiResponse<StationLoginResponseDto>>
    stationLogin(
            @RequestBody StationLoginRequestDto request
    ) {
        StationLoginResponseDto response = authService.authorizeStation(request);
        if (response == null) {
            return ResponseEntity.status(401).body(
                    new ApiResponse<>(false, "STATION_NOT_AUTHORIZED", "STATION_NOT_AUTHORIZED", null)
            );
        }
        return ResponseEntity.ok(
                new ApiResponse<>(true, null, "STATION_AUTHORIZED", response)
        );
    }

    @GetMapping("/me")
    public ResponseEntity<String> me() {

        StationPrincipal station =
                SecurityUtils.getStation();

        return ResponseEntity.ok(
                "Plant=" + station.getPlantId()
                + ", Role=" + station.getRoleDeviceId()
                + ", Host=" + station.getHostname()
                + ", OS=" + station.getOsName()
        );
    }    

    private final AuditService auditService;

    @GetMapping("/audit-test")
    public ResponseEntity<String> auditTest() {
        auditService.register("TEST", "SYSTEM", 1L, "Audit test successful"
        );
        return ResponseEntity.ok("Audit record created");
    }

}