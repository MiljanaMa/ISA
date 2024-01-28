package medequipsystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import medequipsystem.domain.enums.ContractStatus;
import medequipsystem.mapper.MapperUtils.DTOEntity;

import java.time.LocalDate;
import java.time.LocalTime;

public class FullContractDTO implements DTOEntity {
    Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    LocalDate date;
    LocalTime time;

    Integer total;

    CompanyDTO company;

    CompanyEquipmentDTO companyEquipment;

    ContractStatus status;

    public ContractStatus getStatus() {
        return status;
    }

    public void setStatus(ContractStatus status) {
        this.status = status;
    }

    public FullContractDTO(){}
    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public CompanyEquipmentDTO getCompanyEquipment() {
        return companyEquipment;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    public void setCompanyEquipment(CompanyEquipmentDTO equipment) {
        this.companyEquipment = equipment;
    }
}
