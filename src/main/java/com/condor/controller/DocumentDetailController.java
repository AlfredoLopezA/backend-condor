package com.condor.controller;

import com.condor.dto.CreateDocumentDetailRequest;
import com.condor.dto.DocumentDetailDto;
import com.condor.service.DocumentDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentDetailController {

    private final DocumentDetailService service;

    public DocumentDetailController(DocumentDetailService service) {
        this.service = service;
    }

    @PostMapping("/{documentId}/details")
    public ResponseEntity<DocumentDetailDto> create(
            @PathVariable Long documentId,
            @RequestBody CreateDocumentDetailRequest request
    ) {
        return ResponseEntity.ok(
            service.create(documentId, request)
        );
    }

    @GetMapping("/{documentId}/details")
    public ResponseEntity<List<DocumentDetailDto>> findByDocumentId(
            @PathVariable Long documentId
    ) {
        return ResponseEntity.ok(
            service.findByDocumentId(documentId)
        );
    }

    @DeleteMapping("/details/{documentDetailId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long documentDetailId
    ) {
        service.delete(documentDetailId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{documentId}/close-dirty")
    public ResponseEntity<Void> closeDirty(
            @PathVariable Long documentId
    ) {
        service.closeDirtyWeight(documentId);
        return ResponseEntity.ok().build();
    }
}