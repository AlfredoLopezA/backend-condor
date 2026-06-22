package com.condor.controller;

import com.condor.dto.ChangeDocumentContractRequest;
import com.condor.dto.CreateDocumentRequest;
import com.condor.dto.DocumentDto;
import com.condor.service.DocumentDetailService;
import com.condor.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
  private final DocumentService documentService;
  private final DocumentDetailService documentDetailService;
  public DocumentController(DocumentService documentService, DocumentDetailService documentDetailService) {
      this.documentService = documentService;
      this.documentDetailService = documentDetailService;
  }

  @PostMapping
  public ResponseEntity<DocumentDto> create(@RequestBody CreateDocumentRequest req) {
    DocumentDto dto = documentService.create(req);
    return ResponseEntity.ok(dto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DocumentDto> get(@PathVariable Long id) {
    return ResponseEntity.of(documentService.findById(id));
  }

  @PostMapping("/{documentId}/finish")
  public ResponseEntity<Void> finish(@PathVariable Long documentId) {
      documentDetailService.finishDocument(documentId);
      return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{documentId}")
  public ResponseEntity<Void> delete(@PathVariable Long documentId) {
      documentService.delete(documentId);
      return ResponseEntity.noContent().build();
  }

  @PutMapping("/{documentId}/contract")
  public ResponseEntity<Void> changeContract( @PathVariable Long documentId, @RequestBody ChangeDocumentContractRequest request) {
      documentService.changeContract(documentId, request.getContractId());
      return ResponseEntity.noContent().build();
  }

}