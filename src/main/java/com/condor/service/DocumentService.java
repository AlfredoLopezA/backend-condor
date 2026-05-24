package com.condor.service;

import com.condor.dto.CreateDocumentRequest;
import com.condor.dto.DocumentDto;
import com.condor.domain.Document;
import com.condor.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentService {
  private final DocumentRepository repo;
  public DocumentService(DocumentRepository repo){ this.repo = repo; }

  @Transactional
  public DocumentDto create(CreateDocumentRequest req){
    Document d = new Document();
    d.setContractId(req.getContractId());
    d.setPlantId(req.getPlantId());
    d.setDocumentNumber("DOC-" + UUID.randomUUID());
    d.setDocumentDateCreated(Instant.now());
    d = repo.save(d);
    DocumentDto out = new DocumentDto();
    out.setDocumentId(d.getDocumentId());
    out.setDocumentNumber(d.getDocumentNumber());
    out.setContractId(d.getContractId());
    out.setPlantId(d.getPlantId());
    out.setDocumentDateCreated(d.getDocumentDateCreated());
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
      return out;
    });
  }
}
