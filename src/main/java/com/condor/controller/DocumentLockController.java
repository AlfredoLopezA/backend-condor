package com.condor.controller;

import com.condor.dto.DocumentLockResponseDto;
import com.condor.service.DocumentLockService;
import com.condor.security.SecurityUtils;
import com.condor.security.StationPrincipal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
public class DocumentLockController {

    private final DocumentLockService documentLockService;

    public DocumentLockController(DocumentLockService documentLockService) {
        this.documentLockService = documentLockService;
    }

    @PostMapping("/{documentId}/lock")
    public ResponseEntity<DocumentLockResponseDto> acquire(@PathVariable Long documentId) {
        StationPrincipal station = SecurityUtils.getStation();
        return ResponseEntity.ok(documentLockService.acquire(documentId, station.getDeviceId())
        );
    }

    @PostMapping("/{documentId}/lock/heartbeat")
    public ResponseEntity<DocumentLockResponseDto> heartbeat(@PathVariable Long documentId, @RequestHeader("X-Lock-Token") UUID lockToken) {
        StationPrincipal station = SecurityUtils.getStation();
        return ResponseEntity.ok(documentLockService.heartbeat( documentId, station.getDeviceId(), lockToken)
        );
    }

    @DeleteMapping("/{documentId}/lock")
    public ResponseEntity<Void> release(@PathVariable Long documentId, @RequestHeader("X-Lock-Token") UUID lockToken) {
        StationPrincipal station = SecurityUtils.getStation();
        documentLockService.release(documentId, station.getDeviceId(), lockToken);
        return ResponseEntity.noContent().build();
    }
}
