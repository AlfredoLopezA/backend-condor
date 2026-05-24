package com.condor.service;

import com.condor.dto.ActiveContractByPlantDto;
import com.condor.dto.ActiveContractsSummaryDto;
import com.condor.dto.ActiveDocumentsByContractDto;
import com.condor.dto.ContractDto;
import com.condor.dto.DocumentDetailDto;
import com.condor.dto.DocumentHeaderDto;
import com.condor.dto.DocumentRfidDetailDto;
import com.condor.repository.ContractRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractService {
  private final ContractRepository repo;
  private final JdbcTemplate jdbcTemplate;

  public ContractService(ContractRepository repo, JdbcTemplate jdbcTemplate) {
    this.repo = repo;
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<ContractDto> listAll(){
    return repo.findAll().stream().map(c -> {
      ContractDto d = new ContractDto();
      d.setContractId(c.getContractId());
      d.setContractName(c.getContractName());
      d.setCustomerId(c.getCustomerId());
      return d;
    }).collect(Collectors.toList());
  }

  public List<ActiveContractByPlantDto> listActiveContractsByPlant(Short plantId) {
    String sql = "SELECT contract_name, contract_type_id, plant_id, plant_name FROM public.get_active_contracts_by_plant(?)";
    Object arg = plantId; // JdbcTemplate will handle nulls
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ActiveContractByPlantDto.class), arg);
  }

  public List<ActiveContractsSummaryDto> listActiveContractsSummary(Integer plantId) {
    String sql = "SELECT contract_id, contract_name, active_documents, processing_documents FROM public.get_active_contracts_summary(?)";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ActiveContractsSummaryDto.class), plantId);
  }

  public List<ActiveDocumentsByContractDto> listActiveDocumentsByContract(Long contractId, Long plantId) {
    String sql = "SELECT document_id, document_number, document_date_income, document_dirty_weight, document_cage_dirty, document_status_id FROM public.get_active_documents_by_contract(?, ?)";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ActiveDocumentsByContractDto.class), contractId, plantId);
  }

  public Optional<DocumentHeaderDto> getDocumentHeader(Long documentId) {
    String sql = "SELECT * FROM public.get_document_header(?)";
    List<DocumentHeaderDto> results = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DocumentHeaderDto.class), documentId);
    return results.stream().findFirst();
  }

  public List<DocumentDetailDto> getDocumentDetails(Long documentId) {
    String sql = "SELECT document_detail_id, document_id, tare_id, tare_weight, move_type_id, move_type_description, document_detail_weight, document_detail_epc_count, document_detail_epc_ok, document_detail_epc_nr, document_detail_time FROM public.get_document_details(?)";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DocumentDetailDto.class), documentId);
  }

  public List<DocumentRfidDetailDto> getDocumentRfidDetails(Long documentId) {
    String sql = "SELECT product_unit_transaction_id, document_detail_id, product_unit_id, product_unit_transaction_datetime, product_unit_transaction_type_id, product_unit_transaction_valid FROM public.get_document_rfid_details(?)";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DocumentRfidDetailDto.class), documentId);
  }
}
