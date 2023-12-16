package medequipsystem.dto;

import medequipsystem.domain.CompanyEquipment;
import medequipsystem.domain.enums.EquipmentType;
import medequipsystem.mapper.MapperUtils.DTOEntity;

public class CompanyEquipmentProfileDTO implements DTOEntity {

    private Long id;
    private String name;
    private EquipmentType type;
    private String description;
    private double price;

    private int count;

    public CompanyEquipmentProfileDTO(){

    }
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

    public EquipmentType getType() {
        return type;
    }

    public void setType(EquipmentType type) {
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public CompanyEquipmentProfileDTO(CompanyEquipment companyEquipment) {
        this.id = companyEquipment.getId();
        this.name = companyEquipment.getName();
        this.type = companyEquipment.getType();
        this.description = companyEquipment.getDescription();
        this.price = companyEquipment.getPrice();
        this.count = companyEquipment.getCount();
    }
}
