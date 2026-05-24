package com.condor.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

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
}