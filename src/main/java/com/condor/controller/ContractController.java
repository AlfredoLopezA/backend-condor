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

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {
  private final ContractService contractService;
  public ContractController(ContractService cs){ this.contractService = cs; }

  @GetMapping
  public List<ContractDto> list() {
    return contractService.listAll();
  }

  @GetMapping("/active-by-plant")
  public ResponseEntity<List<ActiveContractByPlantDto>> listActiveByPlant(@RequestParam("plantId") Long plantId) {
    if (plantId == null) return ResponseEntity.badRequest().build();
    if (plantId < Short.MIN_VALUE || plantId > Short.MAX_VALUE) return ResponseEntity.badRequest().build();
    Short pid = plantId.shortValue();
    List<ActiveContractByPlantDto> result = contractService.listActiveContractsByPlant(pid);
    return ResponseEntity.ok(result);
  }

  @GetMapping("/active-summary")
  public ResponseEntity<List<ActiveContractsSummaryDto>> listActiveContractsSummary(@RequestParam("plantId") Integer plantId) {
    if (plantId == null) return ResponseEntity.badRequest().build();
    return ResponseEntity.ok(contractService.listActiveContractsSummary(plantId));
  }

  @GetMapping("/active-documents")
  public ResponseEntity<List<ActiveDocumentsByContractDto>> listActiveDocumentsByContract(
      @RequestParam("contractId") Long contractId,
      @RequestParam("plantId") Long plantId) {
    if (contractId == null || plantId == null) return ResponseEntity.badRequest().build();
    return ResponseEntity.ok(contractService.listActiveDocumentsByContract(contractId, plantId));
  }

  @GetMapping("/document-header")
  public ResponseEntity<DocumentHeaderDto> getDocumentHeader(@RequestParam("documentId") Long documentId) {
    if (documentId == null) return ResponseEntity.badRequest().build();
    return contractService.getDocumentHeader(documentId)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/document-details")
  public ResponseEntity<List<DocumentDetailDto>> getDocumentDetails(@RequestParam("documentId") Long documentId) {
    if (documentId == null) return ResponseEntity.badRequest().build();
    List<DocumentDetailDto> details = contractService.getDocumentDetails(documentId);
    return details.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(details);
  }

  @GetMapping("/document-rfid-details")
  public ResponseEntity<List<DocumentRfidDetailDto>> getDocumentRfidDetails(@RequestParam("documentId") Long documentId) {
    if (documentId == null) return ResponseEntity.badRequest().build();
    List<DocumentRfidDetailDto> details = contractService.getDocumentRfidDetails(documentId);
    return details.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(details);
  }
}