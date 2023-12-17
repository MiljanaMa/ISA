package medequipsystem.dto;

import medequipsystem.domain.Company;
import medequipsystem.mapper.MapperUtils.DTOEntity;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CompanyDTO implements DTOEntity {
        private Long id;
        private String name;
        private LocationDTO location;
        private String description;
        private Double averageRate;
        private Set<CompanyAdminRegistrationDTO> companyAdmins;
        //private Set<CompanyEquipmentDTO> equipment;

        private String workingHours;
  
        public CompanyDTO() {
        }

        public CompanyDTO(Company company) {
                id = company.getId();
                name = company.getName();
                description = company.getDescription();
                averageRate = company.getAverageRate();
                location = new LocationDTO(company.getLocation());
                /*companyAdmins = company.getCompanyAdmins().stream()
                        .map(CompanyAdminDTO::new)
                        .collect(Collectors.toSet());
                equipment = company.getEquipment().stream()
                        .map(CompanyEquipmentDTO::new)
                        .collect(Collectors.toSet());*/

                
        }


        public CompanyDTO(Long id, String name, LocationDTO location, String description, Double averageRate) {
                this.id = id;
                this.name = name;
                this.location = location;
                this.description = description;
                this.averageRate = averageRate;
                //this.companyAdmins = companyAdmins;
                //this.equipment = equipment;
        }



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

        public void setLocation(LocationDTO location) {
                this.location = location;
        }

        public String getDescription() {
                return description;
        }

        public String getWorkingHours() {
                return workingHours;
        }

        public void setWorkingHours(String workingHours) {
                this.workingHours = workingHours;
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

       public Set<CompanyAdminRegistrationDTO> getCompanyAdmins() {
                return companyAdmins;
        }

        public void setCompanyAdmins(Set<CompanyAdminRegistrationDTO> companyAdmins) {
                this.companyAdmins = companyAdmins;
        }
        /*
        public Set<CompanyEquipmentDTO> getEquipment() {
                return equipment;
        }

        public void setEquipment(Set<CompanyEquipmentDTO> equipment) {
                this.equipment = equipment;
        }

        */


}
