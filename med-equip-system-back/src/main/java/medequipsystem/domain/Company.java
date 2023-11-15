package medequipsystem.domain;
import javax.persistence.*;
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

    public Company() {
    }

    public Company(Long id, String name, Location location, String description, Double averageRate) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
        this.averageRate = averageRate;
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
}
