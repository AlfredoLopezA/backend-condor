package com.condor.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "document_details")
@Data
public class DocumentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentDetailId;

    private Long documentId;

    private Long tareId;

    private Short moveTypeId;

    private BigDecimal documentDetailWeight;

    private Integer documentDetailEpcCount;

    private Integer documentDetailEpcOk;

    private Integer documentDetailEpcNr;

    private LocalDateTime documentDetailTime;
}