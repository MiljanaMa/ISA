package medequipsystem.mapper;


import medequipsystem.domain.Company;
import medequipsystem.dto.CompanyProfileDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyProfileMapper extends GenericMapper<Company, CompanyProfileDTO>  {}
