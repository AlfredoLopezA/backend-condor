package com.condor.service;

import com.condor.dto.ActiveContractByPlantDto;
import com.condor.dto.ActiveContractsSummaryDto;
import com.condor.dto.ActiveDocumentsByContractDto;
import com.condor.dto.ContractDto;
import com.condor.dto.DocumentDetailDto;
import com.condor.dto.DocumentHeaderDto;
import com.condor.dto.DocumentRfidDetailDto;
import com.condor.repository.ContractRepository;
import com.condor.dto.ContractDocumentsByStatusDto;
import com.condor.dto.ContractDocumentStatusDto;
import com.condor.dto.ContractDocumentsGroupDto;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


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

  public List<ContractDocumentsByStatusDto> listDocumentsByStatus(Long plantId, List<Short> statuses) {
      String statusList = statuses.stream().map(String::valueOf).collect(Collectors.joining(","));
      String sql = """
        SELECT c.contract_id, c.contract_name, c.contract_type_id, ct.contract_type_description, d.document_id, d.document_number, d.document_status_id,
            d.document_date_income, d.document_dirty_weight, d.document_cage_dirty
        FROM contract_plants cp
        INNER JOIN contracts c
            ON c.contract_id = cp.contract_id
        INNER JOIN documents d
            ON d.contract_id = c.contract_id
        AND d.plant_id = cp.plant_id
        INNER JOIN contract_types ct
        ON ct.contract_type_id = c.contract_type_id
        WHERE cp.plant_id = ?
        AND c.contract_active = true AND d.document_status_id IN (%s) AND CURRENT_DATE >= cp.contract_plant_start AND 
        (cp.contract_plant_end IS NULL OR CURRENT_DATE <= cp.contract_plant_end)
        ORDER BY c.contract_name, d.document_number
        """.formatted(statusList);
      return jdbcTemplate.query(sql,
              new BeanPropertyRowMapper<>(ContractDocumentsByStatusDto.class),
              plantId
      );
  }

  private static class ContractDocumentRow {
    private Long contractId;
    private String contractName;
    private Short contractTypeId;
    private String contractTypeDescription;
    private Long documentId;
    private String documentNumber;
    private Long documentStatusId;
    private java.time.LocalDateTime documentDateIncome;
    private java.math.BigDecimal documentDirtyWeight;
    private Short documentCageDirty;

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public Short getContractTypeId() {
        return contractTypeId;
    }

    public void setContractTypeId(Short contractTypeId) {
        this.contractTypeId = contractTypeId;
    }

    public String getContractTypeDescription() {
        return contractTypeDescription;
    }

    public void setContractTypeDescription(String contractTypeDescription) {
        this.contractTypeDescription = contractTypeDescription;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Long getDocumentStatusId() {
        return documentStatusId;
    }

    public void setDocumentStatusId(Long documentStatusId) {
        this.documentStatusId = documentStatusId;
    }

    public java.time.LocalDateTime getDocumentDateIncome() {
        return documentDateIncome;
    }

    public void setDocumentDateIncome(java.time.LocalDateTime documentDateIncome) {
        this.documentDateIncome = documentDateIncome;
    }

    public java.math.BigDecimal getDocumentDirtyWeight() {
        return documentDirtyWeight;
    }

    public void setDocumentDirtyWeight(java.math.BigDecimal documentDirtyWeight) {
        this.documentDirtyWeight = documentDirtyWeight;
    }

    public Short getDocumentCageDirty() {
        return documentCageDirty;
    }

    public void setDocumentCageDirty(Short documentCageDirty) {
        this.documentCageDirty = documentCageDirty;
    }
  }

  public List<ContractDocumentsGroupDto> listGroupedDocumentsByStatus(Short plantId, List<Short> statuses) {
    if (statuses == null || statuses.isEmpty()) {
        throw new IllegalArgumentException("At least one document status is required");
    }
    String placeholders = statuses.stream().map(status -> "?").collect(Collectors.joining(","));
    String sql = """
        SELECT c.contract_id, c.contract_name, c.contract_type_id, ct.contract_type_description AS contractTypeDescription, d.document_id, d.document_number, d.document_status_id,
            d.document_date_income, d.document_dirty_weight, d.document_cage_dirty
        FROM contract_plants cp
        INNER JOIN contracts c
            ON c.contract_id = cp.contract_id
        INNER JOIN documents d
            ON d.contract_id = c.contract_id
        INNER JOIN contract_types ct
        ON ct.contract_type_id = c.contract_type_id
            WHERE cp.plant_id = ?
        AND d.plant_id = cp.plant_id AND c.contract_active = TRUE AND CURRENT_DATE >= cp.contract_plant_start AND
        (cp.contract_plant_end IS NULL OR CURRENT_DATE <= cp.contract_plant_end)
        AND d.document_status_id IN (%s)
        ORDER BY c.contract_name, d.document_status_id DESC, d.document_number
    """.formatted(placeholders);
    List<Object> params = new ArrayList<>();
    params.add(plantId);
    params.addAll(statuses);
    List<ContractDocumentRow> rows = jdbcTemplate.query(
            sql,
            new BeanPropertyRowMapper<>(ContractDocumentRow.class),
            params.toArray()
    );
    Map<Long, ContractDocumentsGroupDto> grouped = new LinkedHashMap<>();
    for (ContractDocumentRow row : rows) {
        ContractDocumentsGroupDto contract = grouped.computeIfAbsent(
                row.getContractId(),
                contractId -> {
                    ContractDocumentsGroupDto dto = new ContractDocumentsGroupDto();
                    dto.setContractId(row.getContractId());
                    dto.setContractName(row.getContractName());
                    dto.setContractTypeId(row.getContractTypeId());
                    dto.setContractTypeDescription(row.getContractTypeDescription());
                    dto.setActiveDocuments(0L);
                    dto.setProcessingDocuments(0L);
                    return dto;
                }
        );
        ContractDocumentStatusDto document = new ContractDocumentStatusDto();
        document.setDocumentId(row.getDocumentId());
        document.setDocumentNumber(row.getDocumentNumber());
        document.setDocumentStatusId(row.getDocumentStatusId());
        document.setDocumentDateIncome(row.getDocumentDateIncome());
        document.setDocumentDirtyWeight(row.getDocumentDirtyWeight());
        document.setDocumentCageDirty(row.getDocumentCageDirty());
        contract.getDocuments().add(document);
        contract.setActiveDocuments(contract.getActiveDocuments() + 1);
        if (row.getDocumentStatusId() == 4L) {
            contract.setProcessingDocuments(contract.getProcessingDocuments() + 1);
        }
    }
    return new ArrayList<>(grouped.values());
  }

}
