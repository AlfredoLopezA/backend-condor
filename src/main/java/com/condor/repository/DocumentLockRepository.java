package com.condor.repository;

import com.condor.domain.DocumentLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface DocumentLockRepository
        extends JpaRepository<DocumentLock, Long> {

    Optional<DocumentLock> findByDocumentIdAndDeviceId(
            Long documentId,
            Long deviceId
    );

    Optional<DocumentLock> findByDocumentIdAndLockToken(
            Long documentId,
            UUID lockToken
    );

    void deleteByDocumentIdAndDeviceId(
            Long documentId,
            Long deviceId
    );

    long deleteByExpiresAtBefore(LocalDateTime now);

    @Modifying
    @Query(
        value = """
            INSERT INTO document_locks (document_id, device_id, lock_token, acquired_at, last_heartbeat_at, expires_at)
            VALUES (:documentId, :deviceId, :lockToken, :now, :now, :expiresAt)
            ON CONFLICT (document_id)
            DO UPDATE SET
                device_id = EXCLUDED.device_id,
                lock_token = EXCLUDED.lock_token,
                acquired_at = EXCLUDED.acquired_at,
                last_heartbeat_at = EXCLUDED.last_heartbeat_at,
                expires_at = EXCLUDED.expires_at
            WHERE document_locks.expires_at < :now
            """,
        nativeQuery = true
    )
    int acquireLock(
            @Param("documentId") Long documentId,
            @Param("deviceId") Long deviceId,
            @Param("lockToken") UUID lockToken,
            @Param("now") LocalDateTime now,
            @Param("expiresAt") LocalDateTime expiresAt
    );

    @Modifying
    @Query("""
        UPDATE DocumentLock dl SET dl.lastHeartbeatAt = :now, dl.expiresAt = :expiresAt 
        WHERE dl.documentId = :documentId AND dl.deviceId = :deviceId AND dl.lockToken = :lockToken
    """)
    int renewLock(
            @Param("documentId") Long documentId,
            @Param("deviceId") Long deviceId,
            @Param("lockToken") UUID lockToken,
            @Param("now") LocalDateTime now,
            @Param("expiresAt") LocalDateTime expiresAt
    );

    @Modifying
    @Query("""
        DELETE FROM DocumentLock dl WHERE dl.documentId = :documentId AND dl.deviceId = :deviceId AND dl.lockToken = :lockToken
    """)
    int releaseLock(
            @Param("documentId") Long documentId,
            @Param("deviceId") Long deviceId,
            @Param("lockToken") UUID lockToken
    );
}
