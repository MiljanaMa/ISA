package medequipsystem.controller;
import medequipsystem.domain.Company;
import medequipsystem.domain.CompanyAdmin;
import medequipsystem.domain.CompanyEquipment;
import medequipsystem.domain.Location;
import medequipsystem.dto.CompanyAdminDTO;
import medequipsystem.dto.CompanyDTO;
import medequipsystem.dto.CompanyEquipmentDTO;
import medequipsystem.mapper.CompanyDTOMapper;
import medequipsystem.service.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping(value = "api/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyDTOMapper companyDTOMapper;

    @GetMapping(value = "/all")
    public ResponseEntity<List<CompanyDTO>> getAll() {
        List<Company> companies = companyService.getAll();
        List<CompanyDTO> companiesDTO = companies.stream()
                .map(companyDTOMapper::fromCompanytoDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(companiesDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO companyDTO) {
        Company companyToCreate = companyDTOMapper.fromDTOtoCompany(companyDTO);
        Company createdCompany = companyService.createOrUpdate(companyToCreate);
        return new ResponseEntity<>(companyDTOMapper.fromCompanytoDTO(createdCompany), HttpStatus.CREATED);
    }

    /*@GetMapping(value = "/all")
    public ResponseEntity<List<CompanyDTO>> getAll() {

        List<Company> companies = companyService.getAll();;

        List<CompanyDTO> companiesDTO = new ArrayList<>();
        for (Company c : companies) {
            companiesDTO.add(new CompanyDTO(c));
        }

        return new ResponseEntity<>(companiesDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO companyDTO) {
        Company companyToCreate = mapDtoToDomain(companyDTO);
        Company createdCompany = companyService.createOrUpdate(companyDTO);
        return new ResponseEntity<>(new CompanyDTO(createdCompany), HttpStatus.CREATED);
    }

    public Company mapDtoToDomain(CompanyDTO companyDTO) {
        Company company = new Company();
        company.setId(companyDTO.getId());
        company.setName(companyDTO.getName());
        company.setDescription(companyDTO.getDescription());
        company.setAverageRate(companyDTO.getAverageRate());

        Location location = companyDTO.getLocation() != null ? companyDTO.getLocation().mapDtoToDomain() : null;
        company.setLocation(location);

        if (companyDTO.getCompanyAdmins() != null && !companyDTO.getCompanyAdmins().isEmpty()) {
            Set<CompanyAdmin> companyAdmins = companyDTO.getCompanyAdmins().stream()
                    .map(adminDTO -> adminDTO.mapDtoToDomain(company))
                    .collect(Collectors.toSet());
            company.setCompanyAdmins(companyAdmins);
        }

        if (companyDTO.getEquipment() != null && !companyDTO.getEquipment().isEmpty()) {
            Set<CompanyEquipment> equipment = companyDTO.getEquipment().stream()
                    .map(companyEquipmentDTO -> companyEquipmentDTO.mapDtoToDomain(company))
                    .collect(Collectors.toSet());
            company.setEquipment(equipment);
        }

        return company;
    }*/

}