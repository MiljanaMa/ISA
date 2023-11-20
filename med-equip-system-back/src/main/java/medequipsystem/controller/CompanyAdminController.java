package medequipsystem.controller;

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
        List<CompanyAdmin> admins = companyAdminService.getAll();
        List<CompanyAdminDTO> adminsDTO = admins.stream()
                .filter(a -> a.getCompany() == null)
                .map(companyAdminDTOMapper::fromCompanyAdmintoDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(adminsDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<CompanyAdminDTO> createCompanyAdmin(@RequestBody CompanyAdminDTO companyAdminDTO) {
        CompanyAdmin companyAdminToCreate = companyAdminDTOMapper.fromDTOtoCompanyAdmin(companyAdminDTO);
        CompanyAdmin createdCompanyAdmin = companyAdminService.create(companyAdminToCreate);
        return new ResponseEntity<>(companyAdminDTOMapper.fromCompanyAdmintoDTO(createdCompanyAdmin), HttpStatus.CREATED);
    }

    /*@GetMapping(value = "/all")
    public ResponseEntity<List<CompanyAdminDTO>> getAll() {
        List<CompanyAdmin> admins = companyAdminService.getAll();;
        List<CompanyAdminDTO> adminsDTO = new ArrayList<>();
        for (CompanyAdmin a : admins) {
            adminsDTO.add(new CompanyAdminDTO(a));
        }
        return new ResponseEntity<>(adminsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/free")
    public ResponseEntity<List<CompanyAdminDTO>> getFree() {
        List<CompanyAdmin> admins = companyAdminService.getAll();;
        List<CompanyAdminDTO> adminsDTO = new ArrayList<>();
        for (CompanyAdmin a : admins) {
            if(a.getCompany() == null){
                adminsDTO.add(new CompanyAdminDTO(a));
            }
        }
        return new ResponseEntity<>(adminsDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<CompanyAdminDTO> createCompanyAdmin(@RequestBody CompanyAdminDTO companyAdminDTO) {
        //CompanyAdmin companyAdminToCreate = mapDtoToDomain(companyAdminDTO);
        CompanyAdmin companyAdminToCreate =
        CompanyAdmin createdCompanyAdmin = companyAdminService.create(companyAdminToCreate);
        return new ResponseEntity<>(new CompanyAdminDTO(createdCompanyAdmin), HttpStatus.CREATED);
    }

    //TODO: napraviti da radi kako treba aaaaa
        //nemam snage vise
    public CompanyAdmin mapDtoToDomain(CompanyAdminDTO companyAdminDTO) {
        CompanyAdmin companyAdmin = new CompanyAdmin();
        companyAdmin.setEmail(companyAdminDTO.getEmail());
        companyAdmin.setPassword(companyAdminDTO.getPassword());
        companyAdmin.setFirstName(companyAdminDTO.getFirstName());
        companyAdmin.setLastName(companyAdminDTO.getLastName());
        companyAdmin.setCity(companyAdminDTO.getCity());
        companyAdmin.setCountry(companyAdminDTO.getCountry());
        companyAdmin.setPhoneNumber(companyAdminDTO.getPhoneNumber());
        //companyAdmin.setCompany(companyService.getById(companyAdminDTO.getCompanyDTO().getId()));
        return companyAdmin;

    }

    }*/
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
