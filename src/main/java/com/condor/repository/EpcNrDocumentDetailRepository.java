package com.condor.repository;

import com.condor.domain.EpcNrDocumentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpcNrDocumentDetailRepository
    extends JpaRepository<EpcNrDocumentDetail, Long> {

    long countByDocumentDetailId(Long documentDetailId);

}