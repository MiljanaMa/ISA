package medequipsystem.dto;

import medequipsystem.domain.Company;

public class CompanyDTO {
        private Long id;
        private String name;
        private LocationDTO location;
        private String description;
        private Double averageRate;

        public  CompanyDTO() {

        }
        public CompanyDTO(Company company) {
                id = company.getId();
                name = company.getName();
                description = company.getDescription();
                averageRate = company.getAverageRate();
                location = new LocationDTO(company.getLocation());

        }

        public CompanyDTO(Long id, String name, LocationDTO location, String description, Double averageRate) {
                this.id = id;
                this.name = name;
                this.location = location;
                this.description = description;
                this.averageRate = averageRate;
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
}
