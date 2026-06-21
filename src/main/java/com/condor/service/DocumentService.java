package com.condor.service;

import com.condor.dto.CreateDocumentRequest;
import com.condor.dto.DocumentDto;
import com.condor.domain.Document;
import com.condor.repository.DocumentRepository;
import com.condor.security.SecurityUtils;
// import com.condor.service.AuditService;
import com.condor.constants.AuditEvents;
import com.condor.constants.AuditEntities;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.Instant;
import java.util.Optional;

@Service
public class DocumentService {
    private final DocumentRepository repo;
    private final AuditService auditService;

    public DocumentService(DocumentRepository repo, AuditService auditService) {
        this.repo = repo;
        this.auditService = auditService;
    }
    @Transactional
    public DocumentDto create(CreateDocumentRequest req){
        Document d = new Document();
        d.setContractId(req.getContractId());
        d.setPlantId( Long.valueOf(SecurityUtils.getPlantId()));
        Instant now = Instant.now();
        String documentNumber = "D" + DateTimeFormatter.ofPattern("yyMMddHHmmssSSS")
                .withZone(ZoneId.systemDefault())
                .format(now);
        d.setDocumentNumber(documentNumber);
        d.setDocumentDateCreated(Instant.now());
        //d.setDocumentDateIncome(req.getDocumentDateIncome());
        d.setDocumentStatusId((short) 1);
        d = repo.save(d);
        auditService.register(AuditEvents.CREATE_DOCUMENT, AuditEntities.DOCUMENT, d.getDocumentId(), "Document created");
        DocumentDto out = new DocumentDto();
        out.setDocumentId(d.getDocumentId());
        out.setDocumentNumber(d.getDocumentNumber());
        out.setContractId(d.getContractId());
        out.setPlantId(d.getPlantId());
        out.setDocumentDateCreated(d.getDocumentDateCreated());
        //out.setDocumentDateIncome(d.getDocumentDateIncome());
        return out;
    }

    public Optional<DocumentDto> findById(Long id){
        return repo.findById(id).map(d -> {
        DocumentDto out = new DocumentDto();
        out.setDocumentId(d.getDocumentId());
        out.setDocumentNumber(d.getDocumentNumber());
        out.setContractId(d.getContractId());
        out.setPlantId(d.getPlantId());
        out.setDocumentDateCreated(d.getDocumentDateCreated());
        out.setDocumentDateIncome(d.getDocumentDateIncome());
        return out;
        });
    }

    @Transactional
    public void delete(Long documentId) {
        Document document = repo.findById(documentId).orElseThrow();
        Short status = document.getDocumentStatusId();
        if (status < 1 || status > 3) {
            throw new RuntimeException("Document cannot be deleted");
        }
        document.setDocumentStatusId((short) 6);
        repo.save(document);
        auditService.register(
                AuditEvents.DELETE_DOCUMENT,
                AuditEntities.DOCUMENT,
                documentId,
                "Document deleted"
        );
    }
}
