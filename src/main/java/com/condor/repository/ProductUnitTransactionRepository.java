package com.condor.repository;

import com.condor.domain.ProductUnitTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductUnitTransactionRepository
        extends JpaRepository<ProductUnitTransaction, Long> {
}