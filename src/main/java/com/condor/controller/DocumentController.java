package com.condor.controller;

import com.condor.dto.CreateDocumentRequest;
import com.condor.dto.DocumentDto;
import com.condor.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
  private final DocumentService documentService;
  public DocumentController(DocumentService ds){ this.documentService = ds; }

  @PostMapping
  public ResponseEntity<DocumentDto> create(@RequestBody CreateDocumentRequest req) {
    DocumentDto dto = documentService.create(req);
    return ResponseEntity.ok(dto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DocumentDto> get(@PathVariable Long id) {
    return ResponseEntity.of(documentService.findById(id));
  }
}