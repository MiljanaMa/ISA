package medequipsystem.controller;
import medequipsystem.domain.User;
import medequipsystem.dto.CompanyAdminRegistrationDTO;
import medequipsystem.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;



    @GetMapping(value = "/all")
    public ResponseEntity<List<CompanyAdminRegistrationDTO>> getAll() {
        List<CompanyAdmin> admins = companyAdminService.getAll();

        /*List<CompanyAdminDTO> adminsDTO = admins.stream()
                .map(companyAdminDTOMapper::fromCompanyAdmintoDTO)
                .collect(Collectors.toList());*/

        List<CompanyAdminRegistrationDTO> adminsDTO = new ArrayList<>();
        for(CompanyAdmin ca : admins){
            adminsDTO.add(new CompanyAdminRegistrationDTO(ca));
        }
        return new ResponseEntity<>(adminsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/free")
    public ResponseEntity<List<CompanyAdminRegistrationDTO>> getFree() {
        List<CompanyAdmin> freeAdmins = companyAdminService.getFreeAdmins();

        /*List<CompanyAdminRegistrationDTO> adminsDTO = freeAdmins.stream()
                .map(companyAdminDTOMapper::fromCompanyAdmintoDTO)
                .collect(Collectors.toList());
        */
        List<CompanyAdminRegistrationDTO> adminsDTO = new ArrayList<>();
        for(CompanyAdmin ca : freeAdmins){
            adminsDTO.add(new CompanyAdminRegistrationDTO(ca));
        }

        return new ResponseEntity<>(adminsDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<CompanyAdminRegistrationDTO> create(@RequestBody CompanyAdminRegistrationDTO companyAdminDTO) {
        /*CompanyAdmin companyAdminToCreate = new CompanyAdmin();

        User user = new User();
        user.setEmail(companyAdminDTO.getEmail());
        user.setPassword(companyAdminDTO.getPassword());
        user.setFirstName(companyAdminDTO.getFirstName());
        user.setLastName(companyAdminDTO.getLastName());
        user.setCity(companyAdminDTO.getCity());
        user.setCountry(companyAdminDTO.getCountry());
        user.setPhoneNumber(companyAdminDTO.getPhoneNumber());
        companyAdminToCreate.setUser(user);

        if (companyAdminDTO.getCompanyId() == 0) {
            companyAdminToCreate.setCompany(null);
        } else {
            Company existingCompany = companyService.getById(companyAdminDTO.getCompanyId());
            companyAdminToCreate.setCompany(existingCompany);
        }*/

        CompanyAdmin companyAdminToCreate = mapDtoToDomain(companyAdminDTO);
        CompanyAdmin createdCompanyAdmin = companyAdminService.create(companyAdminToCreate);
        return new ResponseEntity<>(new CompanyAdminRegistrationDTO(createdCompanyAdmin), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<CompanyAdminRegistrationDTO> update(@RequestBody CompanyAdminRegistrationDTO companyAdminDTO){
        CompanyAdmin companyAdmin = mapDtoToDomain(companyAdminDTO);
        companyAdmin = companyAdminService.update(companyAdmin);
        if(companyAdmin == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new CompanyAdminRegistrationDTO(companyAdmin), HttpStatus.OK);
    }

     @PutMapping(value="update/{id}")
    public ResponseEntity<CompanyAdminRegistrationDTO> updateAdmin(@RequestBody CompanyAdminRegistrationDTO adminDTO) {

        CompanyAdmin updatedAdmin = companyAdminService.update(mapDtoToDomain(adminDTO));
        return new ResponseEntity<>(new CompanyAdminRegistrationDTO(updatedAdmin), HttpStatus.OK);
    }
    @GetMapping(value="{id}")
    public ResponseEntity<CompanyAdminRegistrationDTO> getAdmin(@PathVariable Long id){
        CompanyAdmin admin = companyAdminService.get(id);
        return new ResponseEntity<>(new CompanyAdminRegistrationDTO(admin),HttpStatus.OK);
    }

    //ne moze u companyAdminService zbog cirkularne zavisnosti
    /*
    *
    *
   companyAdminController (field private medequipsystem.service.CompanyAdminService medequipsystem.controller.CompanyAdminController.companyAdminService)
┌─────┐
|  companyAdminService (field private medequipsystem.service.CompanyService medequipsystem.service.CompanyAdminService.companyService)
↑     ↓
|  companyService (field private medequipsystem.service.CompanyAdminService medequipsystem.service.CompanyService.companyAdminService)
└─────┘
*/
    public CompanyAdmin mapDtoToDomain(CompanyAdminRegistrationDTO companyAdminDTO){
        User user = new User();
        user.setEmail(companyAdminDTO.getEmail());
        user.setPassword(passwordEncoder.encode(companyAdminDTO.getPassword()));
        user.setFirstName(companyAdminDTO.getFirstName());
        user.setLastName(companyAdminDTO.getLastName());
        user.setCity(companyAdminDTO.getCity());
        user.setCountry(companyAdminDTO.getCountry());
        user.setPhoneNumber(companyAdminDTO.getPhoneNumber());
        user.setEnabled(true);
        user.setRole(roleRepository.findByName("ROLE_COMPADMIN"));
        CompanyAdmin companyAdmin = new CompanyAdmin();
        companyAdmin.setUser(user);
        if (companyAdminDTO.getCompanyId() == 0) {
            companyAdmin.setCompany(null);
        } else {
            Company existingCompany = companyService.getById(companyAdminDTO.getCompanyId());
            companyAdmin.setCompany(existingCompany);
        }
        return companyAdmin;
    }
}
