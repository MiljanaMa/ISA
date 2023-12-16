package medequipsystem.controller;

import medequipsystem.domain.Company;
import medequipsystem.domain.CompanyEquipment;
import medequipsystem.dto.CompanyDTO;
import medequipsystem.dto.CompanyEquipmentDTO;
import medequipsystem.mapper.EquipmentDTOMapper;
import medequipsystem.service.CompanyEquipmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/equipments")
public class CompanyEquipmentController {

    @Autowired
    private CompanyEquipmentService companyEquipmentService;

    @Autowired
    private EquipmentDTOMapper equipmentDTOMapper;

    @GetMapping(value = "/all")
    public ResponseEntity<List<CompanyEquipmentDTO>> getAll() {
        List<CompanyEquipment> equipments = companyEquipmentService.getAll();

        List<CompanyEquipmentDTO> equipmentDTOS = equipments.stream()
                .map(equipmentDTOMapper::fromTeachertoDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(equipmentDTOS, HttpStatus.OK);
    }
}
