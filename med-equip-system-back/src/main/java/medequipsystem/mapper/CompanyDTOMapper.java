package medequipsystem.mapper;

import medequipsystem.domain.Company;
import medequipsystem.dto.CompanyDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyDTOMapper {
    private static ModelMapper modelMapper;

    @Autowired
    public CompanyDTOMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public  Company fromDTOtoCompany(CompanyDTO dto){
        return modelMapper.map(dto, Company.class);
    }

    public  CompanyDTO fromCompanytoDTO(Company company){
        return modelMapper.map(company, CompanyDTO.class);
    }
}
