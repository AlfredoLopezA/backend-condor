package com.condor.repository;

import com.condor.domain.DocumentDetail;
//import com.condor.dto.DocumentDetailDto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentDetailRepository
        extends JpaRepository<DocumentDetail, Long> {
    List<DocumentDetail> findByDocumentId(Long documentId);
    void deleteByDocumentDetailId(Long documentDetailId);
    List<DocumentDetail> findByDocumentIdAndMoveTypeId(Long documentId, Short moveTypeId);
    
}