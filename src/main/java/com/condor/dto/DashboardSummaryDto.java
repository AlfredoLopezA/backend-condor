package com.condor.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DashboardSummaryDto {
  private Short totalDocuments;
  private Short activeDocuments;
  private Short closedDocuments;
  private Short todayDocuments;
  private Short previousDocuments;
  private BigDecimal totalDirtyWeight;
  private BigDecimal totalCleanWeight;
  private BigDecimal compliancePercentage;
  private Short totalDirtyCages;
  private Short totalDirtyBulks;
  private Short totalCleanCages;
  private Short totalCleanBulks;
}
