package medequipsystem.dto;

import medequipsystem.domain.Company;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CompanyDTO {
        private Long id;
        private String name;
        private LocationDTO location;
        private String description;
        private Double averageRate;
        private Set<CompanyAdminDTO> companyAdmins;
        //private Set<CompanyEquipmentDTO> equipment;


  
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

        public void setDescription(String description) {
                this.description = description;
        }

        public Double getAverageRate() {
                return averageRate;
        }

        public void setAverageRate(Double averageRate) {
                this.averageRate = averageRate;
        }

       public Set<CompanyAdminDTO> getCompanyAdmins() {
                return companyAdmins;
        }

        public void setCompanyAdmins(Set<CompanyAdminDTO> companyAdmins) {
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
