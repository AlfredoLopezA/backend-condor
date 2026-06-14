package com.condor.controller;

import java.util.List;
import com.condor.dto.ActiveContractByPlantDto;
import com.condor.dto.ActiveContractsSummaryDto;
import com.condor.dto.ActiveDocumentsByContractDto;
import com.condor.dto.ContractDto;
import com.condor.dto.DocumentDetailDto;
import com.condor.dto.DocumentHeaderDto;
import com.condor.dto.DocumentRfidDetailDto;
import com.condor.dto.response.ApiResponse;
import com.condor.exception.ResourceNotFoundException;
import com.condor.security.SecurityUtils;
import com.condor.security.StationPrincipal;
import com.condor.service.ContractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/contracts")
@Validated
@Tag(name = "Contracts", description = "Contract operational services")
public class ContractController {
    private final ContractService contractService;
    public ContractController(ContractService cs){ this.contractService = cs; }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ContractDto>>> list() {
        List<ContractDto> contracts = contractService.listAll();
        ApiResponse<List<ContractDto>> response =
            new ApiResponse<>(true, "CONTRACT_LIST_RETRIEVED_SUCCESSFULLY", "CONTRACT_RETRIEVED_SUCCESSFULLY", contracts
            );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/active-by-plant")
    public ResponseEntity<List<ActiveContractByPlantDto>> listActiveByPlant() {
        StationPrincipal station = SecurityUtils.getStation();
        Short plantId = station.getPlantId();
        List<ActiveContractByPlantDto> result = contractService.listActiveContractsByPlant(plantId);
        return ResponseEntity.ok(result);
    }  

    @GetMapping("/active-summary")
    public ResponseEntity<List<ActiveContractsSummaryDto>>
    listActiveContractsSummary() {
        StationPrincipal station = SecurityUtils.getStation();
        return ResponseEntity.ok(
            contractService.listActiveContractsSummary(
                    station.getPlantId().intValue()
            )
        );
    }

    @GetMapping("/active-documents")
    public ResponseEntity<List<ActiveDocumentsByContractDto>>
    listActiveDocumentsByContract(@RequestParam("contractId") Long contractId) {
        StationPrincipal station = SecurityUtils.getStation();
        return ResponseEntity.ok(
            contractService.listActiveDocumentsByContract(
                contractId,
                station.getPlantId().longValue()
            )
        );
    }

    @Operation(
            summary = "Get document header",
            description = "Returns document header information by document id"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Document retrieved successfully"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Document not found"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400",
                description = "Invalid request parameters"
        )
    })
    @GetMapping("/document-header")
    public ResponseEntity<ApiResponse<DocumentHeaderDto>> getDocumentHeader(
        @RequestParam(name ="documentId", required = true) Long documentId) {
            DocumentHeaderDto document = contractService.getDocumentHeader(documentId).orElseThrow(() ->
                new ResourceNotFoundException("DOCUMENT_NOT_FOUND"));
            ApiResponse<DocumentHeaderDto> response =
                new ApiResponse<>( true, "DOCUMENT_RETRIEVED_SUCCESSFULLY", "DOCUMENT_RETRIEVED_SUCCESSFULLY", document
                );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/document-details")
    public ResponseEntity<ApiResponse<List<DocumentDetailDto>>> getDocumentDetails(
            @RequestParam(name = "documentId", required = true) Long documentId) {
        List<DocumentDetailDto> details = contractService.getDocumentDetails(documentId);
        if (details.isEmpty()) {
            throw new ResourceNotFoundException("DOCUMENT_DETAILS_NOT_FOUND");
        }
        ApiResponse<List<DocumentDetailDto>> response = new ApiResponse<>( true,"DOCUMENT_DETAILS_RETRIEVED_SUCCESSFULLY", "DOCUMENT_DETAILS_RETRIEVED_SUCCESSFULLY", details );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/document-rfid-details")
    public ResponseEntity<ApiResponse<List<DocumentRfidDetailDto>>> getDocumentRfidDetails(
            @RequestParam(name = "documentId", required = true) Long documentId) {
        List<DocumentRfidDetailDto> details = contractService.getDocumentRfidDetails(documentId);
        if (details.isEmpty()) {
            throw new ResourceNotFoundException("DOCUMENT_RFID_DETAILS_NOT_FOUND");
        }
        ApiResponse<List<DocumentRfidDetailDto>> response =
                new ApiResponse<>( true, "DOCUMENT_RFID_DETAILS_RETRIEVED_SUCCESSFULLY", "DOCUMENT_RFID_DETAILS_RETRIEVED_SUCCESSFULLY", details );
        return ResponseEntity.ok(response);
    }
}