package com.condor.repository;

import com.condor.domain.ProductUnitTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductUnitTransactionRepository extends JpaRepository<ProductUnitTransaction, Long> {
    @Query(
    value = """
            SELECT p.product_id, p.product_name, COUNT(put.product_unit_transaction_id) AS quantity 
            FROM product_unit_transactions put
            INNER JOIN product_units pu
            ON pu.product_unit_id = put.product_unit_id
            INNER JOIN products p
            ON p.product_id = pu.product_id
            WHERE put.document_detail_id = :documentDetailId
            AND put.product_unit_transaction_valid = TRUE
            GROUP BY p.product_id, p.product_name
            ORDER BY p.product_name
            """,
    nativeQuery = true
    )
    List<Object[]> findProductSummaryByDocumentDetailId(@Param("documentDetailId") Long documentDetailId);
}