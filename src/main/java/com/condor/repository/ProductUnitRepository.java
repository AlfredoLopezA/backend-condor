package com.condor.repository;

import com.condor.domain.ProductUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductUnitRepository
        extends JpaRepository<ProductUnit, Long> {

    Optional<ProductUnit> findByProductUnitEpc(
            String productUnitEpc
    );
}