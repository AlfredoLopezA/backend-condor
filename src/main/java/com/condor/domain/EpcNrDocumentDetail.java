package com.condor.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "epc_nr_document_detail")
@Data
public class EpcNrDocumentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "epc_nr_document_detail_id")
    private Long epcNrDocumentDetailId;

    @Column(name = "document_detail_id")
    private Long documentDetailId;

    @Column(name = "device_id")
    private Long deviceId;

    @Column(name = "epc_nr")
    private String epcNr;
}