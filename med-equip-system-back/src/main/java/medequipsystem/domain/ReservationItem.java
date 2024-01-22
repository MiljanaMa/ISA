package medequipsystem.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "reservationItems")
public class ReservationItem {
    @Id
    @SequenceGenerator(name = "mySeqGenV2", sequenceName = "mySeqV2", initialValue = 30, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV2")
    @Column(name="id", unique=true, nullable=false)
    private Long id;
    @Column(name = "count", nullable = false)
    private int count;

    @JsonIgnore
    @JoinColumn(name = "equipment_id", nullable = false)
    @ManyToOne()
    private CompanyEquipment equipment;
    @Column(name = "price", nullable = false)
    private double price;

    public ReservationItem() {
    }
    public ReservationItem(Long id, int count, CompanyEquipment equipment, double price) {
        this.id = id;
        this.count = count;
        this.equipment = equipment;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public CompanyEquipment getEquipment() {
        return equipment;
    }

    public void setEquipment(CompanyEquipment equipment) {
        this.equipment = equipment;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
