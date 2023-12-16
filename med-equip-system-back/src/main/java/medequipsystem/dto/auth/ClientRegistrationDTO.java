package medequipsystem.dto.auth;

import medequipsystem.domain.Client;

public class ClientRegistrationDTO {
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

    public ClientRegistrationDTO(){}

    public ClientRegistrationDTO(Client client) {
        this.id = client.getUser().getId();
        this.email = client.getUser().getEmail();
        this.password = client.getUser().getPassword();
        this.firstName = client.getUser().getFirstName();
        this.lastName = client.getUser().getLastName();
        this.city = client.getUser().getCity();
        this.country = client.getUser().getCountry();
        this.phoneNumber = client.getUser().getPhoneNumber();
        this.jobTitle = client.getJobTitle();
        this.hospitalInfo = client.getHospitalInfo();
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

}