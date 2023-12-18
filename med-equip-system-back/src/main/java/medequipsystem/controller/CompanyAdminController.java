package medequipsystem.controller;
import medequipsystem.domain.*;
import medequipsystem.dto.CompanyAdminRegistrationDTO;
import medequipsystem.mapper.MapperUtils.DtoUtils;
import medequipsystem.repository.RoleRepository;
import org.apache.coyote.Response;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;


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

@CrossOrigin(origins = "http://localhost:4200/")
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
    public ResponseEntity<CompanyAdminDTO> update(@RequestBody CompanyAdminDTO companyAdminDTO){
        CompanyAdmin companyAdmin = (CompanyAdmin) new DtoUtils().convertToEntity(new CompanyAdmin(), companyAdminDTO);
        companyAdmin.setCompany(companyAdminService.getById(companyAdminDTO.getId()).getCompany());
        companyAdmin.getUser().setRole(new Role(2, "ROLE_COMPADMIN"));
        companyAdmin.getUser().setEnabled(true);
        companyAdmin.getUser().setPassword(passwordEncoder.encode(companyAdmin.getUser().getPassword()));
        companyAdminService.create(companyAdmin);
        return new ResponseEntity<>(companyAdminDTO, HttpStatus.OK);
    }

     @PutMapping(value="update/{id}")
    public ResponseEntity<CompanyAdminRegistrationDTO> updateAdmin(@RequestBody CompanyAdminRegistrationDTO adminDTO) {

        CompanyAdmin updatedAdmin = companyAdminService.update(mapDtoToDomain(adminDTO));
        return new ResponseEntity<>(new CompanyAdminRegistrationDTO(updatedAdmin), HttpStatus.OK);
    }
    @GetMapping(value="{id}")
    public ResponseEntity<CompanyAdminDTO> getAdmin(@PathVariable Long id){
        CompanyAdmin admin = companyAdminService.get(id);
        return new ResponseEntity<>((CompanyAdminDTO) new DtoUtils().convertToDto(admin, new CompanyAdminDTO()), HttpStatus.OK);
    }

    @GetMapping(value="/byUser/{id}")
    public ResponseEntity<CompanyAdminDTO> getAdminByUser(@PathVariable Long id){
        CompanyAdmin admin = companyAdminService.getByUserId(id);
        return new ResponseEntity<>((CompanyAdminDTO) new DtoUtils().convertToDto(admin, new CompanyAdminDTO()), HttpStatus.OK);

    }

    @GetMapping(value="user/{id}")
    public ResponseEntity<CompanyAdminRegistrationDTO> getAdminByUserId(@PathVariable Long id){
        CompanyAdmin admin = companyAdminService.getByUserId(id);
        System.out.println("\n\n*********\nadmin id" + admin.getId().toString());
        System.out.println("*********\nadmin company id " + admin.getCompany().getId().toString());
        admin.setCompany(companyService.getById(admin.getCompany().getId()));
        System.out.println("\n\n*********\nadmin company id " + admin.getCompany().getId().toString());
        System.out.println("*********\ncomp name" + admin.getCompany().getName());
        System.out.println("*********\n company description " + admin.getCompany().getDescription()
        );


        return new ResponseEntity<>(new CompanyAdminRegistrationDTO(admin),HttpStatus.OK);
    }

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
