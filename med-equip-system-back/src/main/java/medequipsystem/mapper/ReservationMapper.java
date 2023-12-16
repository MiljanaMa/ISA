package medequipsystem.mapper;

import medequipsystem.domain.Reservation;
import medequipsystem.dto.ReservationDTO;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface ReservationMapper extends GenericMapper<Reservation, ReservationDTO>  {}
