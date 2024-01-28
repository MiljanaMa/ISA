package medequipsystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import medequipsystem.domain.Contract;
import medequipsystem.domain.enums.ContractStatus;
import medequipsystem.mapper.MapperUtils.DTOEntity;

import java.time.LocalDate;
import java.time.LocalTime;

public class ContractDTO implements DTOEntity {
    Long id;

    Integer date;
    LocalTime time;

    Integer total;

    String companyName;

    String equipmentName;

    ContractStatus status;

    public ContractStatus getStatus() {
        return status;
    }

    public void setStatus(ContractStatus status) {
        this.status = status;
    }

    public ContractDTO(){}
    public Long getId() {
        return id;
    }

    public Integer getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public Integer getTotal() {
        return total;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }
}

