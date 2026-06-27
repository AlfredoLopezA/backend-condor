package com.condor.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_unit_transactions")
@Data
public class ProductUnitTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_unit_transaction_id")
    private Long productUnitTransactionId;

    @Column(name = "product_unit_id")
    private Long productUnitId;

    @Column(name = "document_detail_id")
    private Long documentDetailId;

    @Column(name = "device_id")
    private Long deviceId;

    @Column(name = "product_unit_transaction_datetime")
    private LocalDateTime productUnitTransactionDatetime;

    @Column(name = "product_unit_transaction_valid")
    private Boolean productUnitTransactionValid;
}