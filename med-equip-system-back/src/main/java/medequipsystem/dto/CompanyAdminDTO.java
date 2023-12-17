package medequipsystem.dto;

import medequipsystem.domain.CompanyAdmin;
import medequipsystem.mapper.MapperUtils.DTOEntity;

public class CompanyAdminDTO implements DTOEntity {
    private Long id;
    private UserDTO user;

    private CompanyDTO company;
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

    /*
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
    */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }
}
