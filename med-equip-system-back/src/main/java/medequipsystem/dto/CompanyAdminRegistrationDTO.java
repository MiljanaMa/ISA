package medequipsystem.dto;

import medequipsystem.domain.CompanyAdmin;

public class CompanyAdminRegistrationDTO {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String city;
    private String country;
    private String phoneNumber;
    private Long companyId;

    public CompanyAdminRegistrationDTO(){}

    public CompanyAdminRegistrationDTO(CompanyAdmin companyAdmin) {
        this.id = companyAdmin.getId();
        this.email = companyAdmin.getUser().getEmail();
        this.password = companyAdmin.getUser().getPassword();
        this.firstName = companyAdmin.getUser().getFirstName();
        this.lastName = companyAdmin.getUser().getLastName();
        this.city = companyAdmin.getUser().getCity();
        this.country = companyAdmin.getUser().getCountry();
        this.phoneNumber = companyAdmin.getUser().getPhoneNumber();
        if(companyAdmin.getCompany().getId() == null){
            System.out.println("\n\n****comp id je null " + companyAdmin.getCompany().getId().toString());

            this.companyId = null;
        }else {
            this.companyId = companyAdmin.getCompany().getId();
            System.out.println("\n\n****comp id je broj " + companyAdmin.getCompany().getId().toString());

        }
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
