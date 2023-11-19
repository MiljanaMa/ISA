package medequipsystem.domain;

import javax.persistence.*;

@Entity
@Table(name = "loyaltyPrograms")
public class LoyaltyProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "loyaltyType", nullable = false)
    private String loyaltyType;
    @Column(name = "minPoints", nullable = false)
    private int minPoints;
    @Column(name = "maxPenaltyPoints", nullable = false)
    private int maxPenaltyPoints;
    @Column(name = "discount", nullable = false)
    private double discount;

    public LoyaltyProgram() {}

    public LoyaltyProgram(Long id, String loyaltyType, int minPoints, int maxPenaltyPoints, double discount) {
        this.id = id;
        this.loyaltyType = loyaltyType;
        this.minPoints = minPoints;
        this.maxPenaltyPoints = maxPenaltyPoints;
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoyaltyType() {
        return loyaltyType;
    }

    public void setLoyaltyType(String loyaltyType) {
        this.loyaltyType = loyaltyType;
    }

    public int getMinPoints() {
        return minPoints;
    }

    public void setMinPoints(int minPoints) {
        this.minPoints = minPoints;
    }

    public int getMaxPenaltyPoints() {
        return maxPenaltyPoints;
    }

    public void setMaxPenaltyPoints(int maxPenaltyPoints) {
        this.maxPenaltyPoints = maxPenaltyPoints;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
