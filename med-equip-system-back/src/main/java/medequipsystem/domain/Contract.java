package medequipsystem.domain;

import medequipsystem.domain.enums.ContractStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "contracts")
public class
Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private Integer date;

    @Column(name = "time", nullable = false)
    private LocalTime time;

    @Column(name = "total", nullable = false)
    private Integer total;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = true)
    private Company company;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = true)
    private CompanyEquipment companyEquipment;

    @Column(name = "status", nullable = false)
    private ContractStatus status;

    @Column(name = "hospital", nullable = false)
    private String hospital;

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public ContractStatus getStatus() {
        return status;
    }

    public void setStatus(ContractStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public CompanyEquipment getCompanyEquipment() {
        return companyEquipment;
    }

    public void setCompanyEquipment(CompanyEquipment companyEquipment) {
        this.companyEquipment = companyEquipment;
    }


}
