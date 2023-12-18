package medequipsystem.dto;


import medequipsystem.mapper.MapperUtils.DTOEntity;
import medequipsystem.domain.Company;
import medequipsystem.domain.CompanyAdmin;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompanyProfileDTO implements DTOEntity {

    private Long id;
    private String name;
    private LocationDTO location;
    private String description;

    private Double averageRate;
    private Set<CompanyAdminProfileDTO> companyAdmins;

    private Set<CompanyEquipmentProfileDTO> companyEquipment;

    private String workingHours;

    public CompanyProfileDTO() {
    }


    /*public CompanyProfileDTO(Company company, Set<AppointmentDTO> appointments){

        id = company.getId();
        name = company.getName();
        description = company.getDescription();
        averageRate = company.getAverageRate();
        location = new LocationDTO(company.getLocation());

        /*companyAdmins = company.getCompanyAdmins().stream()
                        .map(CompanyAdminDTO::new)
                        .collect(Collectors.toSet());
          companyAdmins = company.getCompanyAdmins();


        Set<CompanyAdminRegistrationDTO> adminDtos = new HashSet<>(); // dto with all user data
        Set<CompanyAdmin> admins = company.getCompanyAdmins();
        for(CompanyAdmin ca : admins){
            adminDtos.add(new CompanyAdminRegistrationDTO(ca));
        }
        companyAdmins = adminDtos;

        companyEquipment = company.getEquipment().stream()
                        .map(CompanyEquipmentProfileDTO::new)
                        .collect(Collectors.toSet());

        this.appointments = appointments;

    }*/


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(Double averageRate) {
        this.averageRate = averageRate;
    }

    public Set<CompanyAdminProfileDTO> getCompanyAdmins() {
        return companyAdmins;
    }

    public void setCompanyAdmins(Set<CompanyAdminProfileDTO> companyAdmins) {
        this.companyAdmins = companyAdmins;
    }

    public Set<CompanyEquipmentProfileDTO> getCompanyEquipment() {
        return companyEquipment;
    }

    public void setCompanyEquipment(Set<CompanyEquipmentProfileDTO> equipment) {
        this.companyEquipment = equipment;
    }


}
