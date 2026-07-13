package com.condor.controller;

import com.condor.dto.CreateDocumentDetailRequest;
import com.condor.dto.CreateRfidReadRequest;
import com.condor.dto.DocumentDetailDto;
import com.condor.service.DocumentDetailService;
import com.condor.dto.DocumentDetailProductSummaryDto;
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

    @PostMapping("/{documentDetailId}/rfid")
    public ResponseEntity<Void> registerRfid(
            @PathVariable Long documentDetailId,
            @RequestBody CreateRfidReadRequest request
    ) {
        service.registerRfidRead(documentDetailId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{documentId}/details")
    public ResponseEntity<List<DocumentDetailDto>> findByDocumentId(
            @PathVariable Long documentId,
            @RequestParam(required = false) Short moveTypeId
    ) {
        return ResponseEntity.ok(
                service.findByDocumentId(
                        documentId,
                        moveTypeId
                )
        );
    }

    @GetMapping("/details/{documentDetailId}/product-summary")
    public ResponseEntity<List<DocumentDetailProductSummaryDto>> findProductSummary(
            @PathVariable Long documentDetailId
    ) {
        return ResponseEntity.ok(
                service.findProductSummary(documentDetailId)
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