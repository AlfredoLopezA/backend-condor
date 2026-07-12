package com.condor.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class DocumentLockResponseDto {
    private Boolean editable;
    private Long documentId;
    private Long deviceId;
    private UUID lockToken;
    private LocalDateTime expiresAt;
}