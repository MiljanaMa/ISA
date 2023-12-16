package medequipsystem.mapper;

import medequipsystem.domain.CompanyAdmin;
import medequipsystem.dto.CompanyAdminDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyAdminDTOMapper {
    private  static ModelMapper modelMapper;

    @Autowired
    public CompanyAdminDTOMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
        //configureMappings();
    }

    public  CompanyAdmin fromDTOtoCompanyAdmin(CompanyAdminDTO dto){
        return modelMapper.map(dto, CompanyAdmin.class);
    }

    public  CompanyAdminDTO fromCompanyAdmintoDTO(CompanyAdmin companyAdmin){
        return modelMapper.map(companyAdmin, CompanyAdminDTO.class);
    }

    /*private static void configureMappings() {
        TypeMap<CompanyAdminDTO, CompanyAdmin> typeMapDTOToDomain = modelMapper.createTypeMap(CompanyAdminDTO.class, CompanyAdmin.class);
        typeMapDTOToDomain.addMappings(mapping -> {
            mapping.map(src -> src.getFirstName(), CompanyAdmin::setFirstName);
            mapping.map(src -> src.getLastName(), CompanyAdmin::setLastName);
        });

        TypeMap<CompanyAdmin, CompanyAdminDTO> typeMapDomainToDTO = modelMapper.createTypeMap(CompanyAdmin.class, CompanyAdminDTO.class);
        typeMapDomainToDTO.addMappings(mapping -> {
            mapping.map(src -> src.getFirstName(), CompanyAdminDTO::setFirstName);
            mapping.map(src -> src.getLastName(), CompanyAdminDTO::setLastName);
        });
    }*/
}
