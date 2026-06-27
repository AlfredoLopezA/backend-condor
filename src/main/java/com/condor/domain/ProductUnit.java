package com.condor.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product_units")
@Data
public class ProductUnit {

    @Id
    @Column(name = "product_unit_id")
    private Long productUnitId;

    @Column(name = "product_unit_epc")
    private String productUnitEpc;
}