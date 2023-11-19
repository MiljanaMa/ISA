package medequipsystem.mapper;

import medequipsystem.domain.CompanyAdmin;
import medequipsystem.dto.CompanyAdminDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyAdminDTOMapper {
    private  static ModelMapper modelMapper;

    @Autowired
    public CompanyAdminDTOMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public  CompanyAdmin fromDTOtoCompanyAdmin(CompanyAdminDTO dto){
        return modelMapper.map(dto, CompanyAdmin.class);
    }

    public  CompanyAdminDTO fromCompanyAdmintoDTO(CompanyAdmin companyAdmin){
        return modelMapper.map(companyAdmin, CompanyAdminDTO.class);
    }
}
