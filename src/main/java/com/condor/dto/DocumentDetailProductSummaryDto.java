package com.condor.dto;

import lombok.Data;

@Data
public class DocumentDetailProductSummaryDto {

    private Long productId;
    private String productName;
    private Long quantity;
    private Boolean unregistered;
}