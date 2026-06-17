package com.condor.service;

import com.condor.domain.Document;
import com.condor.domain.DocumentDetail;
import com.condor.dto.CreateDocumentDetailRequest;
import com.condor.dto.DocumentDetailDto;
import com.condor.repository.DocumentDetailRepository;
import com.condor.repository.DocumentRepository;
import com.condor.constants.AuditEntities;
import com.condor.constants.AuditEvents;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentDetailService {

    private final DocumentDetailRepository repository;
    private final AuditService auditService;
    private final DocumentRepository documentRepository;

    public DocumentDetailService(DocumentDetailRepository repository, DocumentRepository documentRepository, AuditService auditService) {
        this.repository = repository;
        this.documentRepository = documentRepository;
        this.auditService = auditService;
    }

    @Transactional
    public DocumentDetailDto create(Long documentId, CreateDocumentDetailRequest request) {
        Document document = documentRepository.findById(documentId).orElseThrow();
        if (document.getDocumentStatusId() > 2) {
            throw new RuntimeException("Document does not allow new details");
        }

        DocumentDetail detail = new DocumentDetail();
        detail.setDocumentId(documentId);
        detail.setTareId(request.getTareId());
        detail.setMoveTypeId((short) 1);
        detail.setDocumentDetailWeight(request.getDocumentDetailWeight());
        detail.setDocumentDetailEpcCount(0);
        detail.setDocumentDetailEpcOk(0);
        detail.setDocumentDetailEpcNr(0);
        detail.setDocumentDetailTime(LocalDateTime.now());
        detail = repository.save(detail);

        if (document.getDocumentStatusId() == 1) {
            document.setDocumentStatusId((short) 2);

            documentRepository.save(document);
        }
        // documentRepository.findById(documentId)
        //         .ifPresent(document -> {
        //             if (document.getDocumentStatusId() == 1) {
        //                 document.setDocumentStatusId((short) 2);
        //                 documentRepository.save(document);
        //             }
        //         });
        auditService.register(
                AuditEvents.CREATE_DOCUMENT_DETAIL,
                AuditEntities.DOCUMENT_DETAIL,
                detail.getDocumentDetailId(),
                "Document detail created"
        );
        DocumentDetailDto dto = new DocumentDetailDto();
        dto.setDocumentDetailId(detail.getDocumentDetailId());
        dto.setDocumentId(detail.getDocumentId());
        dto.setTareId(detail.getTareId());
        dto.setMoveTypeId(detail.getMoveTypeId());
        dto.setDocumentDetailWeight(detail.getDocumentDetailWeight());
        dto.setDocumentDetailEpcCount(detail.getDocumentDetailEpcCount());
        dto.setDocumentDetailEpcOk(detail.getDocumentDetailEpcOk());
        dto.setDocumentDetailEpcNr(detail.getDocumentDetailEpcNr());
        dto.setDocumentDetailTime(detail.getDocumentDetailTime());
        return dto;
    }

    public List<DocumentDetailDto> findByDocumentId(Long documentId) {
        return repository.findByDocumentId(documentId)
            .stream()
            .map(detail -> {
                DocumentDetailDto dto = new DocumentDetailDto();
                dto.setDocumentDetailId(detail.getDocumentDetailId());
                dto.setDocumentId(detail.getDocumentId());
                dto.setTareId(detail.getTareId());
                dto.setMoveTypeId(detail.getMoveTypeId());
                dto.setDocumentDetailWeight(detail.getDocumentDetailWeight());
                dto.setDocumentDetailEpcCount(detail.getDocumentDetailEpcCount());
                dto.setDocumentDetailEpcOk(detail.getDocumentDetailEpcOk());
                dto.setDocumentDetailEpcNr(detail.getDocumentDetailEpcNr());
                dto.setDocumentDetailTime(detail.getDocumentDetailTime());
                return dto;
            })
            .toList();
    }

    @Transactional
    public void delete(Long documentDetailId) {
        DocumentDetail detail = repository.findById(documentDetailId).orElseThrow();
        Document document = documentRepository.findById(detail.getDocumentId()).orElseThrow();
        if (document.getDocumentStatusId() > 2) {
            throw new RuntimeException("Document is closed");
        }
        repository.delete(detail);
        auditService.register(
                AuditEvents.DELETE_DOCUMENT_DETAIL,
                AuditEntities.DOCUMENT_DETAIL,
                documentDetailId,
                "Document detail deleted"
        );
    }

    @Transactional
    public void closeDirtyWeight(Long documentId) {
        Document document = documentRepository.findById(documentId).orElseThrow();
        if (document.getDocumentStatusId() != 2) {
            throw new RuntimeException(
                    "Document is not in dirty weighing process"
            );
        }
        List<DocumentDetail> details = repository.findByDocumentId(documentId);
        if (details.isEmpty()) {
            throw new RuntimeException("Document has no details");
        }

        BigDecimal totalDirtyWeight =
                details.stream().map(DocumentDetail::getDocumentDetailWeight).reduce(BigDecimal.ZERO, BigDecimal::add);

        int bulkCount =
                (int) details.stream()
                        .filter(d -> d.getTareId() == 1L)
                        .count();

        int cageCount =
                (int) details.stream()
                        .filter(d -> d.getTareId() > 1L)
                        .count();

        document.setDocumentDirtyWeight(
                totalDirtyWeight
        );

        document.setDocumentBulkDirty(
                bulkCount
        );

        document.setDocumentCageDirty(
                cageCount
        );

        document.setDocumentStatusId(
                (short) 3
        );

        documentRepository.save(document);

        auditService.register(
                AuditEvents.CLOSE_DIRTY_WEIGHT,
                AuditEntities.DOCUMENT,
                documentId,
                "Dirty weight closed"
        );
    }
}