package medequipsystem.domain;

import javax.persistence.*;

@Entity
@Table(name = "company_equipments")
public class CompanyEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_item_id", referencedColumnName = "id")
    private CompanyEquipmentItem companyEquipmentItem;

    @Column(name = "count", nullable = false)
    private int count;

    public CompanyEquipment() {
    }

    public CompanyEquipment(Long id, Company company, CompanyEquipmentItem companyEquipmentItem, int count) {
        this.id = id;
        this.company = company;
        this.companyEquipmentItem = companyEquipmentItem;
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

    public CompanyEquipmentItem getCompanyEquipmentItem() {
        return companyEquipmentItem;
    }

    public void setCompanyEquipmentItem(CompanyEquipmentItem companyEquipmentItem) {
        this.companyEquipmentItem = companyEquipmentItem;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

