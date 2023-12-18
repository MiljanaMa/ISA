package medequipsystem.dto;

import medequipsystem.domain.Company;
import medequipsystem.domain.CompanyEquipment;
import medequipsystem.domain.enums.EquipmentType;
import medequipsystem.mapper.MapperUtils.DTOEntity;
import medequipsystem.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class CompanyEquipmentDTO implements DTOEntity {
    private Long id;
    private String name;
    private EquipmentType type;
    private String description;
    private double price;
    private CompanyDTO company;
    private int count;
    private int reservedCount;

    public CompanyEquipmentDTO() {
    }

    public CompanyEquipmentDTO(Long id, String name, EquipmentType type, String description, double price, CompanyDTO company, int count, int reservedCount) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.company = company;
        this.count = count;
        this.reservedCount = reservedCount;
    }

    //@Autowired
   // private CompanyRepository companyRepository;
    public CompanyEquipmentDTO(CompanyEquipment companyEquipment) {
        this.id = companyEquipment.getId();
        this.name = companyEquipment.getName();
        this.type = companyEquipment.getType();
        this.description = companyEquipment.getDescription();
        this.price = companyEquipment.getPrice();
        Company comp = companyEquipment.getCompany();
        if(comp != null){
                this.company = new CompanyDTO(comp);
        } else this.company = null;


        //this.company = companyEquipment.getCompany() != null ? new CompanyDTO(companyEquipment.getCompany()) : null;
        this.count = companyEquipment.getCount();
        this.reservedCount = companyEquipment.getReservedCount();
    }

    public int getReservedCount() {
        return reservedCount;
    }

    public void setReservedCount(int reservedCount) {
        this.reservedCount = reservedCount;
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

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    //TODO: mapper or something will be needed
    //TODO: napraviti da radi kako treba aaaaa
    public CompanyEquipment mapDtoToDomain(Company company) {
        CompanyEquipment companyEquipment = new CompanyEquipment();
        companyEquipment.setId(this.getId());
        companyEquipment.setName(this.getName());
        companyEquipment.setType(this.getType());
        companyEquipment.setDescription(this.getDescription());
        companyEquipment.setPrice(this.getPrice());
        companyEquipment.setCount(this.getCount());
        companyEquipment.setReservedCount(this.getReservedCount());

        if (this.getCompany() != null) {
            companyEquipment.setCompany(company);
        }

        return companyEquipment;
    }
}
