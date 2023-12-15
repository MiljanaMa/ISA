package medequipsystem.mapper;

import medequipsystem.domain.SystemAdmin;
import medequipsystem.dto.CompanyAdminDTO;
import medequipsystem.dto.SystemAdminDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SystemAdminDTOMapper {
    private  static ModelMapper modelMapper;

    @Autowired
    public SystemAdminDTOMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public SystemAdmin fromDTOtoCompanyAdmin(SystemAdminDTO dto){
        return modelMapper.map(dto, SystemAdmin.class);
    }

    public SystemAdminDTO fromSystemAdminToDTO(SystemAdmin systemAdmin){
        return modelMapper.map(systemAdmin, SystemAdminDTO.class);
    }
}
