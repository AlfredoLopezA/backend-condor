package com.condor.dto;

import lombok.Data;
import java.util.List;

@Data
public class CreateRfidReadRequest {
    private Long deviceId;

    private List<String> epcs;
}