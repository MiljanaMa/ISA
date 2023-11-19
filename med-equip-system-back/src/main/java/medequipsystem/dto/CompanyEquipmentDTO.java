package medequipsystem.dto;

import medequipsystem.domain.Company;
import medequipsystem.domain.CompanyEquipment;
import medequipsystem.domain.CompanyEquipmentItem;

public class CompanyEquipmentDTO {
    private Long id;
    private CompanyDTO company;
    private CompanyEquipmentItemDTO companyEquipmentItem;
    private int count;

    public CompanyEquipmentDTO() {
    }

    public CompanyEquipmentDTO(Long id, CompanyDTO company, CompanyEquipmentItemDTO companyEquipmentItem, int count) {
        this.id = id;
        this.company = company;
        this.companyEquipmentItem = companyEquipmentItem;
        this.count = count;
    }

    public CompanyEquipmentDTO(CompanyEquipment companyEquipment) {
        this.id = companyEquipment.getId();
        this.company = companyEquipment.getCompany() != null ? new CompanyDTO(companyEquipment.getCompany()) : null;
        this.companyEquipmentItem = companyEquipment.getCompanyEquipmentItem() != null ?
                new CompanyEquipmentItemDTO(companyEquipment.getCompanyEquipmentItem()) : null;
        this.count = companyEquipment.getCount();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    public CompanyEquipmentItemDTO getCompanyEquipmentItem() {
        return companyEquipmentItem;
    }

    public void setCompanyEquipmentItem(CompanyEquipmentItemDTO companyEquipmentItem) {
        this.companyEquipmentItem = companyEquipmentItem;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public CompanyEquipment mapDtoToDomain() {
        CompanyEquipment companyEquipment = new CompanyEquipment();
        companyEquipment.setId(this.getId());
        companyEquipment.setCount(this.getCount());

        if (this.getCompany() != null) {
            Company company = new Company();
            company.setId(this.getCompany().getId());
            companyEquipment.setCompany(company);
        }

        if (this.getCompanyEquipmentItem() != null) {
            CompanyEquipmentItemDTO equipmentItemDTO = this.getCompanyEquipmentItem();
            CompanyEquipmentItem equipmentItem = new CompanyEquipmentItem();
            equipmentItem.setId(equipmentItemDTO.getId());
            companyEquipment.setCompanyEquipmentItem(equipmentItem);
        }

        return companyEquipment;
    }
}
