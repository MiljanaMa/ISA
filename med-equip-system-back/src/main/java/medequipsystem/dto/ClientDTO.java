package medequipsystem.dto;

import medequipsystem.domain.Client;
import medequipsystem.domain.User;
import medequipsystem.domain.enums.UserType;

public class ClientDTO {

    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String city;
    private String country;
    private String phoneNumber;
    private String jobTitle;
    private String hospitalInfo;
    private UserType userType;
    private int penaltyPoints;
    private int points;
    private String loyaltyType;
    private double discount;
    private boolean emailConfirmed;

    public ClientDTO(){}

    public ClientDTO(Client client) {
        this.id = client.getUser().getId();
        this.email = client.getUser().getEmail();
        this.password = client.getUser().getPassword();
        this.firstName = client.getUser().getFirstName();
        this.lastName = client.getUser().getLastName();
        this.city = client.getUser().getCity();
        this.country = client.getUser().getCountry();
        this.phoneNumber = client.getUser().getPhoneNumber();
        this.userType = client.getUser().getUserType();
        this.jobTitle = client.getJobTitle();
        this.hospitalInfo = client.getHospitalInfo();
        this.penaltyPoints = client.getPenaltyPoints();
        this.points = client.getPoints();
        this.emailConfirmed = client.isEmailConfirmed();
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPenaltyPoints() {
        return penaltyPoints;
    }

    public void setPenaltyPoints(int penaltyPoints) {
        this.penaltyPoints = penaltyPoints;
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

    public String getHospitalInfo() {
        return hospitalInfo;
    }

    public void setHospitalInfo(String hospitalInfo) {
        this.hospitalInfo = hospitalInfo;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getLoyaltyType() {
        return loyaltyType;
    }

    public void setLoyaltyType(String loyaltyType) {
        this.loyaltyType = loyaltyType;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
    public boolean isEmailConfirmed() {
        return emailConfirmed;
    }
    public void setEmailConfirmed(boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }
}
