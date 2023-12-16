package medequipsystem.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "companies")
@JsonIgnoreProperties("company")
public class Company {
    @Id
    @SequenceGenerator(name = "mySeqGenV2", sequenceName = "mySeqV2", initialValue = 30, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV2")
    @Column(name="id", unique=true, nullable=false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "loc_id", referencedColumnName = "id")
    private Location location;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "avgRate", nullable = false)
    private Double averageRate;

    @Column(name = "working_hours", nullable = false)
    private String workingHours;

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CompanyAdmin> companyAdmins = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CompanyEquipment> equipment = new HashSet<>();

    public Company() {
    }

    public Company(Long id, String name, Location location, String description, Double averageRate) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
        this.averageRate = averageRate;
    } //check if needed or delete

    public Company(Long id, String name, Location location, String description, Double averageRate, Set<CompanyAdmin> companyAdmins, Set<CompanyEquipment> equipment) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
        this.averageRate = averageRate;
        this.companyAdmins = companyAdmins;
        this.equipment = equipment;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(Double averageRate) {
        this.averageRate = averageRate;
    }

    public Set<CompanyEquipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(Set<CompanyEquipment> equipment) {
        this.equipment = equipment;
    }

    public Set<CompanyAdmin> getCompanyAdmins() {
        return companyAdmins;
    }

    public void setCompanyAdmins(Set<CompanyAdmin> companyAdmins) {
        this.companyAdmins = companyAdmins;
    }
}
