package medequipsystem.dto;

import medequipsystem.domain.CompanyEquipmentItem;

public class CompanyEquipmentItemDTO {
    private Long id;
    private String name;
    private String type;
    private String description;
    private double price;
    private Long companyId;

    public CompanyEquipmentItemDTO() {
    }

    public CompanyEquipmentItemDTO(Long id, String name, String type, String description, double price, Long companyId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.companyId = companyId;
    }

    public CompanyEquipmentItemDTO(CompanyEquipmentItem companyEquipmentItem) {
        this.id = companyEquipmentItem.getId();
        this.name = companyEquipmentItem.getName();
        this.type = companyEquipmentItem.getType();
        this.description = companyEquipmentItem.getDescription();
        this.price = companyEquipmentItem.getPrice();
        this.companyId = companyEquipmentItem.getCompanyId();
    }

    // Getters and setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
