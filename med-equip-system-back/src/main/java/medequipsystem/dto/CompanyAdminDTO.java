package medequipsystem.dto;

import medequipsystem.domain.Company;
import medequipsystem.domain.CompanyAdmin;

public class CompanyAdminDTO {
    private Long id;
    private UserDTO userDTO;
    /*private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String city;
    private String country;
    private String phoneNumber;*/
    private CompanyDTO companyDTO;
    private Long companyId;
    //private CompanyDTO companyDTO; stack overflow goes brrr

    public CompanyAdminDTO() {
    }

    /*public CompanyAdminDTO(Long id, String email, String password, String firstName, String lastName,
                           String city, String country, String phoneNumber, Long companyId) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.country = country;
        this.phoneNumber = phoneNumber;
        //this.companyDTO = companyDto;
        this.companyId = companyId;

    }*/

    public CompanyAdminDTO(CompanyAdmin companyAdmin) {
        this.id = companyAdmin.getId();
        //this.userDTO.setId(companyAdmin.);
        this.userDTO.setEmail(companyAdmin.getUser().getEmail());
        this.userDTO.setPassword(companyAdmin.getUser().getPassword());
        this.userDTO.setFirstName(companyAdmin.getUser().getFirstName());
        this.userDTO.setLastName(companyAdmin.getUser().getLastName());
        this.userDTO.setCity(companyAdmin.getUser().getCity());
        this.userDTO.setCountry(companyAdmin.getUser().getCountry());
        this.userDTO.setPhoneNumber(companyAdmin.getUser().getPhoneNumber());
        this.companyId = companyAdmin.getCompany().getId();
        //this.companyDTO = companyAdmin.getCompany() != null ? new CompanyDTO(companyAdmin.getCompany()) : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
/*
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
*/
    /*public CompanyDTO getCompanyDTO() {
        return companyDTO;
    }

    public void setCompanyDTO(CompanyDTO companyDTO) {
        this.companyDTO = companyDTO;
    }*/

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
