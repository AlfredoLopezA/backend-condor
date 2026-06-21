package com.condor.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "documents")
@Data
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    private String documentNumber;
    private Long contractId;
    private Long plantId;
    private Instant documentDateCreated;
    private LocalDate documentDateIncome;
    private LocalDate documentDateExit;
    private Short documentStatusId;
    private BigDecimal documentDirtyWeight;
    private Integer documentCageDirty;
    private Integer documentBulkDirty;
    private BigDecimal documentCleanWeight;
    private Integer documentCageClean;
    private Integer documentBulkClean;
    private Integer documentEpcCount;
    private Integer documentEpcOk;
    private Integer documentEpcNr;
}