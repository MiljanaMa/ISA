package medequipsystem.dto;

import java.util.Set;

public class CompanyRegistrationDTO {
    private Long id;
    private String name;
    private LocationDTO location;
    private String description;
    private Double averageRate;
    private Set<CompanyAdminRegistrationDTO> companyAdmins;

    public CompanyRegistrationDTO(){
    }




}
