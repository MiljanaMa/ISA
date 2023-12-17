package medequipsystem.mapper;

import medequipsystem.domain.Appointment;
import medequipsystem.dto.AppointmentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper extends GenericMapper<Appointment, AppointmentDTO>{
}
