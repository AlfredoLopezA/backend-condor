package com.condor.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "contracts")
@Data
public class Contract {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long contractId;

  private Long customerId;
  private Long contractTypeId;
  private String contractName;
}