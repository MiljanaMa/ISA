package medequipsystem.controller;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import medequipsystem.domain.*;
import medequipsystem.dto.*;
import medequipsystem.mapper.MapperUtils.DtoUtils;
import medequipsystem.service.CompanyService;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping(value = "api/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping(value = "/all")
    public ResponseEntity<Set<CompanyDTO>> getAll() {
        try{
        Set<Company> companies = new HashSet<>(companyService.getAll());
        Set<CompanyDTO> companiesDTO = (Set<CompanyDTO>) new DtoUtils().convertToDtos(companies, new CompanyDTO());
        return new ResponseEntity<>(companiesDTO, HttpStatus.OK);
        }catch(RequestNotPermitted r){
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
    }


    @PostMapping(value = "/create")
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO companyDTO) {
        Company createdCompany = companyService.createOrUpdate(companyDTO);
        CompanyDTO dto = (CompanyDTO) new DtoUtils().convertToDto(createdCompany, new CompanyDTO());
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyProfileDTO> getById(@PathVariable Long id) {
        Company c = companyService.getById(id);

        CompanyProfileDTO dto = (CompanyProfileDTO) new DtoUtils().convertToDto(c, new CompanyProfileDTO());

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Void> updateCompany(@RequestBody CompanyProfileDTO companyDTO) {
        Company company = (Company) new DtoUtils().convertToEntity( new Company(), companyDTO);
        company.setCompanyEquipment(new HashSet<>());
        company.setCompanyAdmins(new HashSet<>());
        companyService.Update(company);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}