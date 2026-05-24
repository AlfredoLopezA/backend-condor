package com.condor.service;

import com.condor.dto.DashboardSummaryDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class DashboardService {
  private final JdbcTemplate jdbcTemplate;

  public DashboardService(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public DashboardSummaryDto getSummary(LocalDate date) {
    BeanPropertyRowMapper<DashboardSummaryDto> mapper = new BeanPropertyRowMapper<>(DashboardSummaryDto.class);
    LocalDate effectiveDate = date == null ? LocalDate.now() : date;
    return jdbcTemplate.queryForObject(
      "SELECT * FROM public.get_dashboard_summary(?)",
      mapper,
      Date.valueOf(effectiveDate));
  }
}
