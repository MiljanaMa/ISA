package medequipsystem.mapper;

import medequipsystem.domain.Location;
import medequipsystem.dto.LocationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper extends GenericMapper<Location, LocationDTO>{
}
