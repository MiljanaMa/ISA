package medequipsystem.mapper;


import medequipsystem.domain.CompanyAdmin;
import medequipsystem.dto.CompanyAdminDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper extends GenericMapper<CompanyAdmin, CompanyAdminDTO>{
}
