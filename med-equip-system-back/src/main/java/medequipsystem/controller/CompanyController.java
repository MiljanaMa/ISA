package medequipsystem.controller;
import medequipsystem.domain.Company;
import medequipsystem.dto.CompanyDTO;
import medequipsystem.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping(value = "api/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<CompanyDTO>> getAll() {

        List<Company> companies = companyService.getAll();;

        List<CompanyDTO> companiesDTO = new ArrayList<>();
        for (Company c : companies) {
            companiesDTO.add(new CompanyDTO(c));
        }

        return new ResponseEntity<>(companiesDTO, HttpStatus.OK);
    }
}