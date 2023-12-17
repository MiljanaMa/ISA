package medequipsystem.service;

import medequipsystem.domain.*;
import medequipsystem.dto.CompanyAdminDTO;
import medequipsystem.dto.CompanyAdminRegistrationDTO;
import medequipsystem.dto.CompanyDTO;
import medequipsystem.dto.CompanyEquipmentDTO;
import medequipsystem.mapper.CompanyAdminDTOMapper;
import medequipsystem.repository.CompanyAdminRepository;
import medequipsystem.repository.CompanyRepository;
import medequipsystem.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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

    @Autowired
    private CompanyAdminDTOMapper companyAdminDTOMapper;


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

    public void Update(Company company){
        companyRepository.save(company);
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

        List<Long> companyAdminIds = new ArrayList<>();

        if (companyDTO.getCompanyAdmins() != null && !companyDTO.getCompanyAdmins().isEmpty()) {
            Set<CompanyAdmin> companyAdmins = new HashSet<>();

            for (CompanyAdminRegistrationDTO adminDTO : companyDTO.getCompanyAdmins()) {
                companyAdminIds.add(adminDTO.getId());
                CompanyAdmin ca = companyAdminService.getById(adminDTO.getId());
                companyAdmins.add(ca); //dodaj kompaniji admina koji je pronadjen preko id-a iz liste admina prosledjenih iz companyDto
            }
            company.setCompanyAdmins(companyAdmins);
        }
        System.out.println("admin ids count: " + companyAdminIds.size());

        Company savedCompany =  companyRepository.save(company);
        System.out.println("admin ids: " + companyAdminIds);
        System.out.println("saved company id: " + savedCompany.getId());


        for(Long adminId : companyAdminIds){
            CompanyAdmin ca = companyAdminService.connectWithCompany(adminId, savedCompany);
        }// aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa

        /*for(CompanyAdmin adminToUpdatCompany : savedCompany.getCompanyAdmins()){
            adminToUpdatCompany.setCompany(savedCompany);
            adminToUpdatCompany = companyAdminService.update(adminToUpdatCompany);
        }*/

        //TODO: ovde bi mozda trebalo da se kompaniji dodeli ova lista povezanih admina, pa da se update... mada mozda i ne mora
        //TODO: trebace mozda da se doradi za opremu

        /*if (companyDTO.getEquipment() != null && !companyDTO.getEquipment().isEmpty()) {
            Set<CompanyEquipment> equipment = companyDTO.getEquipment().stream()
                    .map(companyEquipmentDTO -> {
                        CompanyEquipment companyEquipment = companyEquipmentDTO.mapDtoToDomain(company);
                        return  companyEquipmentService.create(companyEquipment);
                    })
                    .collect(Collectors.toSet());
            company.setEquipment(equipment);
        }*/

        return savedCompany;
    }
}
