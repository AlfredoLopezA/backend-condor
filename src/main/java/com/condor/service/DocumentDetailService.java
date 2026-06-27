package com.condor.service;

import com.condor.domain.Document;
import com.condor.domain.DocumentDetail;
import com.condor.domain.ProductUnit;
import com.condor.domain.ProductUnitTransaction;
import com.condor.domain.EpcNrDocumentDetail;
import com.condor.dto.CreateDocumentDetailRequest;
import com.condor.dto.CreateRfidReadRequest;
import com.condor.dto.DocumentDetailDto;
import com.condor.repository.DocumentDetailRepository;
import com.condor.repository.DocumentRepository;
import com.condor.repository.EpcNrDocumentDetailRepository;
import com.condor.repository.ProductUnitRepository;
import com.condor.repository.ProductUnitTransactionRepository;
import com.condor.constants.AuditEntities;
import com.condor.constants.AuditEvents;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentDetailService {

    private final DocumentDetailRepository repository;
    private final AuditService auditService;
    private final DocumentRepository documentRepository;
    private final ProductUnitRepository productUnitRepository;
    private final ProductUnitTransactionRepository productUnitTransactionRepository;
    private final EpcNrDocumentDetailRepository epcNrDocumentDetailRepository;

    public DocumentDetailService(DocumentDetailRepository repository, DocumentRepository documentRepository, 
        ProductUnitRepository productUnitRepository, ProductUnitTransactionRepository productUnitTransactionRepository, 
        EpcNrDocumentDetailRepository epcNrDocumentDetailRepository, AuditService auditService) {
        this.repository = repository;
        this.documentRepository = documentRepository;
        this.auditService = auditService;
        this.productUnitRepository = productUnitRepository;
        this.productUnitTransactionRepository = productUnitTransactionRepository;
        this.epcNrDocumentDetailRepository = epcNrDocumentDetailRepository;
    }

    @Transactional
    public DocumentDetailDto create(Long documentId, CreateDocumentDetailRequest request) {
        Document document = documentRepository.findById(documentId).orElseThrow();
        Short status = document.getDocumentStatusId();
        if (status >= 5) {
            throw new RuntimeException("Document does not allow new details");
        }
        DocumentDetail detail = new DocumentDetail();
        detail.setDocumentId(documentId);
        detail.setTareId(request.getTareId());
        if (status < 3) {
            detail.setMoveTypeId((short) 1);
        } else if (status == 3 || status == 4) {
            detail.setMoveTypeId((short) 2);
        } else {
            throw new RuntimeException("Invalid document status");
        }
        detail.setDocumentDetailWeight(request.getDocumentDetailWeight());
        detail.setDocumentDetailEpcCount(0);
        detail.setDocumentDetailEpcOk(0);
        detail.setDocumentDetailEpcNr(0);
        detail.setDocumentDetailTime(LocalDateTime.now());
        detail = repository.save(detail);
        if (document.getDocumentStatusId() == 1 && detail.getMoveTypeId() == 1) {
            document.setDocumentStatusId((short) 2);
            documentRepository.save(document);
        }

        if (document.getDocumentStatusId() == 3 && detail.getMoveTypeId() == 2) {
            document.setDocumentStatusId((short) 4);
            documentRepository.save(document);
        }
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
        Short status = document.getDocumentStatusId();
        if (status != 2 && status != 4) {
            throw new RuntimeException("Document does not allow detail deletion");
        }

        repository.delete(detail);
        if (status == 2) {
            long remainingDirtyDetails = repository.findByDocumentId(document.getDocumentId())
                            .stream().filter(d -> d.getMoveTypeId() == 1).count();
            if (remainingDirtyDetails == 0) {
                document.setDocumentStatusId((short) 1);
                documentRepository.save(document);
            }
        }
        if (status == 4) {
            long remainingCleanDetails = repository.findByDocumentId(document.getDocumentId())
                            .stream().filter(d -> d.getMoveTypeId() == 2)
                            .count();
            if (remainingCleanDetails == 0) {
                document.setDocumentStatusId((short) 3);
                documentRepository.save(document);
            }
        }

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
        if (document.getDocumentTypeId().equals((short) 3)
                || document.getDocumentTypeId().equals((short) 4)) {
            throw new RuntimeException("Document type does not allow dirty weighing");
        }
        if (document.getDocumentStatusId() != 2) {
            throw new RuntimeException("Document is not in dirty weighing process");
        }
        List<DocumentDetail> details = repository.findByDocumentId(documentId);
        if (details.isEmpty()) {
            throw new RuntimeException("Document has no details");
        }

        BigDecimal totalDirtyWeight = details.stream().map(DocumentDetail::getDocumentDetailWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
        int bulkCount = (int) details.stream().filter(d -> d.getTareId() == 1L).count();
        int cageCount = (int) details.stream().filter(d -> d.getTareId() > 1L).count();
        document.setDocumentDirtyWeight(totalDirtyWeight);
        document.setDocumentBulkDirty(bulkCount);
        document.setDocumentCageDirty(cageCount);
        document.setDocumentDateIncome(LocalDate.now());
        document.setDocumentStatusId((short) 3);
        documentRepository.save(document);
        auditService.register(
                AuditEvents.CLOSE_DIRTY_WEIGHT,
                AuditEntities.DOCUMENT,
                documentId,
                "Dirty weight closed"
        );
    }

    @Transactional
    public void finishDocument(Long documentId) {
        Document document = documentRepository.findById(documentId).orElseThrow();
        Short status = document.getDocumentStatusId();
        if (status != 3 && status != 4) {
            throw new RuntimeException("Document cannot be finalized");
        }

        List<DocumentDetail> details = repository.findByDocumentId(documentId);
        boolean hasCleanDetails = details.stream().anyMatch(d -> d.getMoveTypeId() == 2);
        if (!hasCleanDetails) {
            throw new RuntimeException("Document has no clean details");
        }
        BigDecimal cleanWeight = BigDecimal.ZERO;
        int cageClean = 0;
        int bulkClean = 0;
        int epcCount = 0;
        int epcOk = 0;
        int epcNr = 0;
        for (DocumentDetail detail : details) {
            if (detail.getMoveTypeId() != 2) {
                continue;
            }
            cleanWeight = cleanWeight.add(detail.getDocumentDetailWeight());
            if (detail.getTareId() == 1) {
                bulkClean++;
            } else {
                cageClean++;
            }
            epcCount += detail.getDocumentDetailEpcCount();
            epcOk += detail.getDocumentDetailEpcOk();
            epcNr += detail.getDocumentDetailEpcNr();
        }
        document.setDocumentCleanWeight(cleanWeight);
        document.setDocumentCageClean(cageClean);
        document.setDocumentBulkClean(bulkClean);
        document.setDocumentEpcCount(epcCount);
        document.setDocumentEpcOk(epcOk);
        document.setDocumentEpcNr(epcNr);
        document.setDocumentDateExit(LocalDate.now());
        document.setDocumentStatusId((short) 5);
        documentRepository.save(document);
        auditService.register(
                AuditEvents.FINISH_DOCUMENT,
                AuditEntities.DOCUMENT,
                documentId,
                "Document finished"
        );
    }

    @Transactional
    public void registerRfidRead(Long documentDetailId, CreateRfidReadRequest request) {
        DocumentDetail detail = repository.findById(documentDetailId).orElseThrow();
        if (detail.getMoveTypeId() != 2) {
            throw new RuntimeException("RFID can only be registered on clean details");
        }

        //int epcCount = request.getEpcs().size();
        int epcCount = 0;
        int epcOk = 0;
        int epcNr = 0;
        for (String epc : request.getEpcs()) {
            epcCount++;
            Optional<ProductUnit> productUnit = productUnitRepository.findByProductUnitEpc(epc);
            if (productUnit.isPresent()) {
                ProductUnitTransaction transaction = new ProductUnitTransaction();
                transaction.setProductUnitId(productUnit.get().getProductUnitId());
                transaction.setDocumentDetailId(documentDetailId);
                transaction.setDeviceId(request.getDeviceId());
                transaction.setProductUnitTransactionDatetime(LocalDateTime.now());
                transaction.setProductUnitTransactionValid(true);
                productUnitTransactionRepository.save(transaction);
                epcOk++;

            } else {
                EpcNrDocumentDetail epcNrDetail = new EpcNrDocumentDetail();
                epcNrDetail.setDocumentDetailId(documentDetailId);
                epcNrDetail.setDeviceId(request.getDeviceId());
                epcNrDetail.setEpcNr(epc);
                epcNrDocumentDetailRepository.save(epcNrDetail);
                epcNr++;
            }
        }
        detail.setDocumentDetailEpcCount(epcCount);
        detail.setDocumentDetailEpcOk(epcOk);
        detail.setDocumentDetailEpcNr(epcNr);
        repository.save(detail);
    }
}