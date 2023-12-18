package medequipsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import medequipsystem.domain.Company;
import medequipsystem.domain.CompanyEquipment;
import medequipsystem.dto.CompanyDTO;
import medequipsystem.dto.CompanyEquipmentDTO;
import medequipsystem.dto.CompanyEquipmentProfileDTO;
import medequipsystem.dto.CompanyProfileDTO;
import medequipsystem.mapper.EquipmentDTOMapper;
import medequipsystem.mapper.MapperUtils.DtoUtils;
import medequipsystem.service.CompanyEquipmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping(value = "api/equipments")
public class CompanyEquipmentController {

    @Autowired
    private CompanyEquipmentService companyEquipmentService;

    @Autowired
    private EquipmentDTOMapper equipmentDTOMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(value = "/all")
    public ResponseEntity<List<CompanyEquipmentDTO>> getAll() {
        List<CompanyEquipment> equipments = companyEquipmentService.getAll();

        List<CompanyEquipmentDTO> equipmentDTOS = equipments.stream()
                .map(equipmentDTOMapper::fromTeachertoDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(equipmentDTOS, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<CompanyEquipmentProfileDTO> create(@RequestBody Map<String, Object> request){

        CompanyEquipmentProfileDTO companyEquipmentDTO = objectMapper.convertValue(request.get("equipDto"), CompanyEquipmentProfileDTO.class);
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
        equipment.setCompany(null);
        companyEquipmentService.update(equipment);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
