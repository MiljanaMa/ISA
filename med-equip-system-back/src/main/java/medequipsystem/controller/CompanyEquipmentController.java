package medequipsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import medequipsystem.domain.Company;
import medequipsystem.domain.CompanyEquipment;
import medequipsystem.dto.CompanyEquipmentDTO;
import medequipsystem.dto.CompanyEquipmentProfileDTO;
import medequipsystem.dto.CompanyProfileDTO;
import medequipsystem.mapper.MapperUtils.DtoUtils;
import medequipsystem.service.CompanyEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping(value = "api/equipments")
public class CompanyEquipmentController {

    @Autowired
    private CompanyEquipmentService companyEquipmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(value = "/all")
    public ResponseEntity<Set<CompanyEquipmentDTO>> getAll() {
        Set<CompanyEquipment> equipments = new HashSet<>(companyEquipmentService.getAll());
        Set<CompanyEquipmentDTO> equipmentDTOS = (Set<CompanyEquipmentDTO>) new DtoUtils().convertToDtos(equipments, new CompanyEquipmentDTO());
        return new ResponseEntity<>(equipmentDTOS, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<CompanyEquipmentProfileDTO> create(@RequestBody Map<String, Object> request){

        CompanyEquipmentProfileDTO companyEquipmentDTO = (CompanyEquipmentProfileDTO) new DtoUtils().convertToDto(request, new CompanyEquipmentProfileDTO());
        //objectMapper.convertValue(request.get("equipDto"), CompanyEquipmentProfileDTO.class);
        CompanyProfileDTO companyDTO = objectMapper.convertValue(request.get("companyDto"), CompanyProfileDTO.class );

        CompanyEquipment equipment = (CompanyEquipment) new DtoUtils().convertToEntity(new CompanyEquipment(), companyEquipmentDTO);
        Company company = (Company) new DtoUtils().convertToEntity(new Company(), companyDTO);
        equipment.setCompany(company);
        CompanyEquipment result = companyEquipmentService.create(equipment);
        return new ResponseEntity<>((CompanyEquipmentProfileDTO) new DtoUtils().convertToDto(result, new CompanyEquipmentProfileDTO()), HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Void> update(@RequestBody Map<String, Object> request){
        CompanyEquipmentProfileDTO companyEquipmentDTO = objectMapper.convertValue(request.get("equipDto"), CompanyEquipmentProfileDTO.class);
        CompanyProfileDTO companyDTO = objectMapper.convertValue(request.get("companyDto"), CompanyProfileDTO.class );

        CompanyEquipment equipment = (CompanyEquipment) new DtoUtils().convertToEntity(new CompanyEquipment(), companyEquipmentDTO);

        Company company = (Company) new DtoUtils().convertToEntity(new Company(), companyDTO);
        equipment.setCompany(company);
        companyEquipmentService.update(equipment);
        return new ResponseEntity<>(HttpStatus.OK);


    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        CompanyEquipment equipment = companyEquipmentService.getById(id);
        if(!companyEquipmentService.IsUnpicked(id)) {
            equipment.setCompany(null);
            companyEquipmentService.update(equipment);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
