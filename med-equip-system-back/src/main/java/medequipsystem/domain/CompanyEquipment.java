package medequipsystem.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import medequipsystem.domain.enums.EquipmentType;

import javax.persistence.*;

@Entity
@Table(name = "company_equipments")
public class CompanyEquipment {
    @Id
    @SequenceGenerator(name = "mySeqGenV2", sequenceName = "mySeqV2", initialValue = 30, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV2")
    @Column(name="id", unique=true, nullable=false)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private EquipmentType type;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private double price;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "count", nullable = false)
    private int count;

    /*@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_item_id", referencedColumnName = "id")
    private CompanyEquipmentItem companyEquipmentItem;*/

    public CompanyEquipment() {
    }

    public CompanyEquipment(Long id, String name, EquipmentType type, String description, double price, Company company, int count) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.company = company;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
}

