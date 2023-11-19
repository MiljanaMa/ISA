package medequipsystem.mapper;

import medequipsystem.domain.CompanyEquipment;
import medequipsystem.dto.CompanyEquipmentDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EquipmentDTOMapper {

    private static ModelMapper modelMapper;

    @Autowired
    public EquipmentDTOMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public  CompanyEquipment fromDTOtoTeacher(CompanyEquipmentDTO dto) {
        return modelMapper.map(dto, CompanyEquipment.class);
    }

    public  CompanyEquipmentDTO fromTeachertoDTO(CompanyEquipment companyEquipment) {
        return modelMapper.map(companyEquipment, CompanyEquipmentDTO.class);
    }
}
