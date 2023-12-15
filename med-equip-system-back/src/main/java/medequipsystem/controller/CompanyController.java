package medequipsystem.controller;
import medequipsystem.domain.*;
import medequipsystem.dto.*;
import medequipsystem.mapper.GenericMapper;
import medequipsystem.repository.AppointmentRepository;
import medequipsystem.service.AppointmentService;
import medequipsystem.service.CompanyService;
import org.apache.coyote.Response;
import medequipsystem.mapper.CompanyDTOMapper;
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
    private AppointmentService appointmentService;

    @Autowired
    private CompanyDTOMapper companyDTOMapper;


    @Autowired
    private GenericMapper<Company,CompanyProfileDTO> companyMapper;

    @Autowired
    private GenericMapper<CompanyEquipment, CompanyEquipmentProfileDTO> equipmentProfileMapper;

    @Autowired
    private GenericMapper<Appointment, AppointmentDTO> appointmentMapper;

    @Autowired
    private GenericMapper<Location, LocationDTO> locationMapper;

    @Autowired
    private GenericMapper<CompanyAdmin, CompanyAdminDTO> adminMapper;


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
        Company createdCompany = companyService.createOrUpdate(companyDTO);
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
    }*/

    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyProfileDTO> getById(@PathVariable Long id) {
        Company c = companyService.getById(id);
        Set<Appointment> appointments = appointmentService.getByCompany(id);

        CompanyProfileDTO dto = companyMapper.toDto(c);
        dto.setCompanyAdmins(adminMapper.toDto(c.getCompanyAdmins()));
        dto.setLocation(locationMapper.toDto(c.getLocation()));
        dto.setCompanyEquipment(equipmentProfileMapper.toDto(c.getEquipment()));
        dto.setAppointments(appointmentMapper.toDto(appointments));

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    /*
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
    }
    */


    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Void> updateCompany(@RequestBody CompanyProfileDTO companyDTO) {
        CompanyDTO dto = new CompanyDTO(companyDTO.getId(), companyDTO.getName(), companyDTO.getLocation(), companyDTO.getDescription(), companyDTO.getAverageRate());
        Company updatedCompany = companyService.createOrUpdate(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}