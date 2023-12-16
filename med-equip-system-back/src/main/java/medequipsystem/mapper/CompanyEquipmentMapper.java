package medequipsystem.mapper;

import medequipsystem.domain.CompanyEquipment;
import medequipsystem.dto.CompanyEquipmentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyEquipmentMapper extends GenericMapper<CompanyEquipment, CompanyEquipmentDTO>  {}
