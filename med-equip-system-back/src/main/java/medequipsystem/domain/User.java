package medequipsystem.domain;

import medequipsystem.domain.enums.LoyaltyType;
import medequipsystem.domain.enums.UserType;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "firstName", nullable = false)
    private String firstName;
    @Column(name = "lastName", nullable = false)
    private String lastName;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "country", nullable = false)
    private String country; //change this later (city + country)
    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;
    @Column(name = "jobTitle", nullable = false)
    private String jobTitle;
    @Column(name = "companyInformation", nullable = false)
    private String companyInformation;
    @Column(name = "userType", nullable = false)
    private UserType userType;
    @Column(name = "penalPoints", nullable = true)
    private int penalPoints;
    @Column(name = "loyaltyType", nullable = true)
    private LoyaltyType loyaltyType;

    public User() {}

    public User(String email, String password, String firstName, String lastName, String city, String country, String phoneNumber, String jobTitle, String companyInformation, UserType userType,
                int penalPoints, LoyaltyType loyaltyType) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.jobTitle = jobTitle;
        this.companyInformation = companyInformation;
        this.userType = userType;
        this.penalPoints = penalPoints;
        this.loyaltyType = loyaltyType;
    }

    public int getPenalPoints() {
        return penalPoints;
    }

    public void setPenalPoints(int penalPoints) {
        this.penalPoints = penalPoints;
    }

    public LoyaltyType getLoyaltyType() {
        return loyaltyType;
    }

    public void setLoyaltyType(LoyaltyType loyaltyType) {
        this.loyaltyType = loyaltyType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyInformation() {
        return companyInformation;
    }

    public void setCompanyInformation(String companyInformation) {
        this.companyInformation = companyInformation;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }


}


