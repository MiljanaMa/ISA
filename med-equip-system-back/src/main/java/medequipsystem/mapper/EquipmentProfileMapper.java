package medequipsystem.mapper;

import medequipsystem.domain.CompanyEquipment;
import medequipsystem.dto.CompanyEquipmentProfileDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EquipmentProfileMapper extends GenericMapper<CompanyEquipment,CompanyEquipmentProfileDTO>{
}
