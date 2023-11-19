package medequipsystem.dto;

import medequipsystem.domain.Company;
import medequipsystem.domain.CompanyAdmin;

public class CompanyAdminDTO {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String city;
    private String country;
    private String phoneNumber;
    //private CompanyDTO companyDTO; stack overflow goes brrr

    public CompanyAdminDTO() {
    }

    public CompanyAdminDTO(Long id, String email, String password, String firstName, String lastName,
                           String city, String country, String phoneNumber, CompanyDTO companyDto) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.country = country;
        this.phoneNumber = phoneNumber;
        //this.companyDTO = companyDto;
    }

    public CompanyAdminDTO(CompanyAdmin companyAdmin) {
        this.id = companyAdmin.getId();
        this.email = companyAdmin.getEmail();
        this.password = companyAdmin.getPassword();
        this.firstName = companyAdmin.getFirstName();
        this.lastName = companyAdmin.getLastName();
        this.city = companyAdmin.getCity();
        this.country = companyAdmin.getCountry();
        this.phoneNumber = companyAdmin.getPhoneNumber();
        //this.companyDTO = companyAdmin.getCompany() != null ? new CompanyDTO(companyAdmin.getCompany()) : null;
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

    /*public CompanyDTO getCompanyDTO() {
        return companyDTO;
    }

    public void setCompanyDTO(CompanyDTO companyDTO) {
        this.companyDTO = companyDTO;
    }*/

    //TODO: napraviti da radi kako treba aaaaa
    public CompanyAdmin mapDtoToDomain(Company company) {
        CompanyAdmin companyAdmin = new CompanyAdmin();
        companyAdmin.setEmail(this.getEmail());
        companyAdmin.setPassword(this.getPassword());
        companyAdmin.setFirstName(this.getFirstName());
        companyAdmin.setLastName(this.getLastName());
        companyAdmin.setCity(this.getCity());
        companyAdmin.setCountry(this.getCountry());
        companyAdmin.setPhoneNumber(this.getPhoneNumber());
        if (company != null) {
            companyAdmin.setCompany(company);
        }
        return companyAdmin;
    }
}
