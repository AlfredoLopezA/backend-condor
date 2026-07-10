package com.condor.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ContractDocumentsGroupDto {

    private Long contractId;
    private String contractName;
    private Short contractTypeId;
    private Long activeDocuments;
    private Long processingDocuments;
    private List<ContractDocumentStatusDto> documents = new ArrayList<>();
}
