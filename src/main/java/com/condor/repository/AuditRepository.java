package com.condor.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuditRepository {
    private final JdbcTemplate jdbcTemplate;
    public AuditRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveAudit(Long deviceId, String eventType, String entityName, Long entityId, String description) {
        jdbcTemplate.update(
                """
                INSERT INTO audit_log
                (
                    device_id,
                    event_type,
                    entity_name,
                    entity_id,
                    description
                )
                VALUES (?, ?, ?, ?, ?)
                """,
                deviceId, eventType, entityName, entityId, description
        );
    }
}