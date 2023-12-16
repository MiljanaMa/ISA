package medequipsystem.mapper;

import medequipsystem.domain.ReservationItem;
import medequipsystem.dto.ReservationItemDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationItemMapper extends GenericMapper<ReservationItem, ReservationItemDTO>  {}
