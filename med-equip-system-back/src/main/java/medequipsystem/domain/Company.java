package medequipsystem.domain;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private Set<CompanyAdmin> companyAdmins = new HashSet<>();
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
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
