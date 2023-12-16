package medequipsystem.controller;
import org.springframework.web.bind.annotation.CrossOrigin;


import medequipsystem.domain.Company;
import medequipsystem.domain.CompanyAdmin;
import medequipsystem.domain.Location;
import medequipsystem.dto.CompanyAdminDTO;
import medequipsystem.dto.CompanyDTO;
import medequipsystem.mapper.CompanyAdminDTOMapper;
import medequipsystem.service.CompanyAdminService;
import medequipsystem.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/companyadmins")
public class CompanyAdminController {
    @Autowired
    private CompanyAdminService companyAdminService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyAdminDTOMapper companyAdminDTOMapper;

    @GetMapping(value = "/all")
    public ResponseEntity<List<CompanyAdminDTO>> getAll() {
        List<CompanyAdmin> admins = companyAdminService.getAll();
        List<CompanyAdminDTO> adminsDTO = admins.stream()
                .map(companyAdminDTOMapper::fromCompanyAdmintoDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(adminsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/free")
    public ResponseEntity<List<CompanyAdminDTO>> getFree() {
        List<CompanyAdmin> freeAdmins = companyAdminService.getFreeAdmins();

        List<CompanyAdminDTO> adminsDTO = freeAdmins.stream()
                .map(companyAdminDTOMapper::fromCompanyAdmintoDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(adminsDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/create") //clean code left the chat
    public ResponseEntity<CompanyAdminDTO> create(@RequestBody CompanyAdminDTO companyAdminDTO) {
        CompanyAdmin companyAdminToCreate = new CompanyAdmin();

        companyAdminToCreate.setEmail(companyAdminDTO.getEmail());
        companyAdminToCreate.setPassword(companyAdminDTO.getPassword());
        companyAdminToCreate.setFirstName(companyAdminDTO.getFirstName());
        companyAdminToCreate.setLastName(companyAdminDTO.getLastName());
        companyAdminToCreate.setCity(companyAdminDTO.getCity());
        companyAdminToCreate.setCountry(companyAdminDTO.getCountry());
        companyAdminToCreate.setPhoneNumber(companyAdminDTO.getPhoneNumber());

        if (companyAdminDTO.getCompanyId() == 0) {
            companyAdminToCreate.setCompany(null);
        } else {
            Company existingCompany = companyService.getById(companyAdminDTO.getCompanyId());
            companyAdminToCreate.setCompany(existingCompany);
        }

        CompanyAdmin createdCompanyAdmin = companyAdminService.create(companyAdminToCreate);
        return new ResponseEntity<>(companyAdminDTOMapper.fromCompanyAdmintoDTO(createdCompanyAdmin), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<CompanyAdminDTO> update(@RequestBody CompanyAdminDTO companyAdminDTO){
        CompanyAdmin companyAdmin = companyAdminDTOMapper.fromDTOtoCompanyAdmin(companyAdminDTO);
        companyAdmin = companyAdminService.update(companyAdmin);
        if(companyAdmin == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(companyAdminDTOMapper.fromCompanyAdmintoDTO(companyAdmin), HttpStatus.OK);
    }

     @PutMapping(value="update/{id}")
    public ResponseEntity<CompanyAdminDTO> updateAdmin(@RequestBody CompanyAdminDTO adminDTO) {

        CompanyAdmin updatedAdmin = companyAdminService.update(adminDTO);
        return new ResponseEntity<>(new CompanyAdminDTO(updatedAdmin), HttpStatus.OK);
    }
    @GetMapping(value="{id}")
    public ResponseEntity<CompanyAdminDTO> getAdmin(@PathVariable Long id){
        CompanyAdmin admin = companyAdminService.get(id);
        return new ResponseEntity<>(new CompanyAdminDTO(admin),HttpStatus.OK);
    }
}
