package com.condor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Audit event information")
public class AuditLogDto {
    @Schema(
        description = "Device identifier",
        example = "1"
    )
    private Long deviceId;
    @Schema(
        description = "Audit event type",
        example = "CREATE_DOCUMENT"
    )
    private String eventType;
    @Schema(
        description = "Affected entity",
        example = "DOCUMENT"
    )
    private String entityName;
    @Schema(
        description = "Affected entity identifier",
        example = "12345"
    )
    private Long entityId;
    @Schema(
        description = "Event description",
        example = "Document created"
    )
    private String description;
}