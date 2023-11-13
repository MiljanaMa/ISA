package medequipsystem.dto;

import medequipsystem.domain.User;
import medequipsystem.enums.UserType;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class UserDTO {

    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String city;
    private String country; //change this later (city + country)
    private String phoneNumber;
    private String jobTitle;
    private String companyInformation;
    private UserType userType;

    public UserDTO(Long id, String email, String password, String firstName, String lastName, String city,
                   String country, String phoneNumber, String jobTitle, String companyInformation, UserType userType) {
        this.id = id;
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
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.city = user.getCity();
        this.country = user.getCountry();
        this.phoneNumber = user.getPhoneNumber();
        this.jobTitle = user.getJobTitle();
        this.companyInformation = user.getCompanyInformation();
        this.userType = user.getUserType();
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
