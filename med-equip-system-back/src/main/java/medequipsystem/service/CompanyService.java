package medequipsystem.service;

import medequipsystem.domain.*;
import medequipsystem.dto.CompanyDTO;
import medequipsystem.dto.CompanyEquipmentDTO;
import medequipsystem.repository.CompanyRepository;
import medequipsystem.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyAdminService companyAdminService;

    @Autowired
    private  CompanyEquipmentService companyEquipmentService;
    @Autowired
    private LocationRepository locationRepository;


    public List<Company> getAll() {
        return this.companyRepository.findAll();
    }

    public Company createOrUpdate(Company company) {
        return companyRepository.save(company);
    }

    public Company getById(Long id){
        Optional<Company> companyOptional = this.companyRepository.findById(id);
        return companyOptional.orElse(null);
    }

    public Company createOrUpdate(CompanyDTO companyDTO) {
        Company company = new Company();
        company.setId(companyDTO.getId());
        company.setName(companyDTO.getName());
        company.setDescription(companyDTO.getDescription());
        company.setAverageRate(companyDTO.getAverageRate());

        Location location = companyDTO.getLocation() != null ? companyDTO.getLocation().mapDtoToDomain() : null;
        locationRepository.save(location);
        company.setLocation(location);


        /*if (companyDTO.getCompanyAdmins() != null && !companyDTO.getCompanyAdmins().isEmpty()) {
            Set<CompanyAdmin> companyAdmins = companyDTO.getCompanyAdmins().stream()
                    .map(adminDTO -> {
                        CompanyAdmin admin = adminDTO.mapDtoToDomain(company);
                        return companyAdminService.create(admin);
                    })
                    .collect(Collectors.toSet());
            company.setCompanyAdmins(companyAdmins);
        }*/

        /*if (companyDTO.getEquipment() != null && !companyDTO.getEquipment().isEmpty()) {
            Set<CompanyEquipment> equipment = companyDTO.getEquipment().stream()
                    .map(companyEquipmentDTO -> {
                        CompanyEquipment companyEquipment = companyEquipmentDTO.mapDtoToDomain(company);
                        return  companyEquipmentService.create(companyEquipment);
                    })
                    .collect(Collectors.toSet());
            company.setEquipment(equipment);
        }*/

        return companyRepository.save(company);
    }
}
