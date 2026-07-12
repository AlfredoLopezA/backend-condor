package com.condor.service;

import com.condor.domain.DocumentLock;
import com.condor.dto.DocumentLockResponseDto;
import com.condor.repository.DocumentLockRepository;
import com.condor.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class DocumentLockService {
    private static final long LOCK_DURATION_MINUTES = 2;
    private final DocumentLockRepository documentLockRepository;
    private final DocumentRepository documentRepository;
    public DocumentLockService(DocumentLockRepository documentLockRepository, DocumentRepository documentRepository) {
        this.documentLockRepository = documentLockRepository;
        this.documentRepository = documentRepository;
    }

    @Transactional
    public DocumentLockResponseDto acquire(Long documentId, Long deviceId) {
        if (!documentRepository.existsById(documentId)) { throw new RuntimeException("Document not found"); }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusMinutes(LOCK_DURATION_MINUTES);
        UUID lockToken = UUID.randomUUID();
        int affectedRows = documentLockRepository.acquireLock(documentId, deviceId, lockToken, now, expiresAt);
        DocumentLockResponseDto response = new DocumentLockResponseDto();
        response.setDocumentId(documentId);
        response.setEditable(affectedRows == 1);
        if (affectedRows == 1) {
            response.setDeviceId(deviceId);
            response.setLockToken(lockToken);
            response.setExpiresAt(expiresAt);
            return response;
        }
        DocumentLock existingLock = documentLockRepository.findById(documentId).orElseThrow();
        if (existingLock.getDeviceId().equals(deviceId)) {
            response.setEditable(true);
            response.setDeviceId(deviceId);
            response.setLockToken(existingLock.getLockToken());
            response.setExpiresAt(existingLock.getExpiresAt());
            return response;
        }
        response.setDeviceId(existingLock.getDeviceId());
        response.setLockToken(null);
        response.setExpiresAt(existingLock.getExpiresAt());
        return response;
    }

    @Transactional
    public DocumentLockResponseDto heartbeat(Long documentId, Long deviceId, UUID lockToken) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusMinutes(LOCK_DURATION_MINUTES);
        int affectedRows = documentLockRepository.renewLock(documentId, deviceId, lockToken, now, expiresAt);
        if (affectedRows == 0) {
            throw new RuntimeException("Document lock is not valid or no longer belongs to this device");
        }
        DocumentLockResponseDto response = new DocumentLockResponseDto();
        response.setEditable(true);
        response.setDocumentId(documentId);
        response.setDeviceId(deviceId);
        response.setLockToken(lockToken);
        response.setExpiresAt(expiresAt);
        return response;
    }

    @Transactional
    public void release(Long documentId, Long deviceId, UUID lockToken) {
        int affectedRows = documentLockRepository.releaseLock(documentId, deviceId, lockToken);
        if (affectedRows == 0) {
            throw new RuntimeException("Document lock is not valid or no longer belongs to this device");
        }
    }
}
