package com.condor.controller;

import com.condor.dto.ActiveContractByPlantDto;
import com.condor.dto.ActiveContractsSummaryDto;
import com.condor.dto.ActiveDocumentsByContractDto;
import com.condor.dto.ContractDto;
import com.condor.dto.DocumentDetailDto;
import com.condor.dto.DocumentHeaderDto;
import com.condor.dto.DocumentRfidDetailDto;
import com.condor.service.ContractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.condor.dto.response.ApiResponse;
import com.condor.exception.ResourceNotFoundException;
import org.springframework.validation.annotation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
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
  public ResponseEntity<ApiResponse<List<ActiveContractByPlantDto>>> listActiveByPlant(
          @RequestParam(name = "plantId", required = true) Long plantId) {
      if (plantId < Short.MIN_VALUE || plantId > Short.MAX_VALUE) {
          throw new IllegalArgumentException("PLANT_ID_OUT_RANGE");
      }
      Short pid = plantId.shortValue();
      List<ActiveContractByPlantDto> result =
              contractService.listActiveContractsByPlant(pid);
      ApiResponse<List<ActiveContractByPlantDto>> response =
              new ApiResponse<>( true, "ACTIVE_CONTRACT_BY_PLANT_RETRIEVED_SUCCESSFULLY", "ACTIVE_CONTRACT_RETRIEVED_SUCCESSFULLY", result );
      return ResponseEntity.ok(response);
  }
  
  @GetMapping("/active-summary")
  public ResponseEntity<ApiResponse<List<ActiveContractsSummaryDto>>> listActiveContractsSummary(
          @RequestParam(name = "plantId", required = true) Integer plantId) {
      List<ActiveContractsSummaryDto> result = contractService.listActiveContractsSummary(plantId);
      ApiResponse<List<ActiveContractsSummaryDto>> response = new ApiResponse<>( true, "ACTIVE_CONTRACT_SUMMARY_RETRIEVED_SUCCESSFULY", "ACTIVE_CONTRACT_SUMMARY_RETRIEVED_SUCCESSFULY", result );
      return ResponseEntity.ok(response);
  }

  @GetMapping("/active-documents")
  public ResponseEntity<ApiResponse<List<ActiveDocumentsByContractDto>>> listActiveDocumentsByContract(
          @RequestParam(name = "contractId", required = true) Long contractId,
          @RequestParam(name = "plantId", required = true) Long plantId) {
      List<ActiveDocumentsByContractDto> result =
              contractService.listActiveDocumentsByContract(contractId, plantId);
      ApiResponse<List<ActiveDocumentsByContractDto>> response = new ApiResponse<>( true, "ACTIVE_DOCUMENTS_RETRIEVED_SUCCSSFULLY", "ACTIVE_DOCUMENTS_RETRIEVED_SUCCSSFULLY", result );
      return ResponseEntity.ok(response);
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