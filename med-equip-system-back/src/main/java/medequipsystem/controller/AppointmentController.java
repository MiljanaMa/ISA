package medequipsystem.controller;


import medequipsystem.domain.Appointment;
import medequipsystem.dto.AppointmentDTO;
import medequipsystem.mapper.MapperUtils.DtoUtils;
import medequipsystem.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping(value = "api/appointments")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;
    @PostMapping(value = "/create")
    public ResponseEntity<Void> createAppointment(@RequestBody AppointmentDTO appointmentDTO){
        Appointment appointment = (Appointment) new DtoUtils().convertToEntity(new Object(), appointmentDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/company/{id}")
    public ResponseEntity<Set<AppointmentDTO>> getAppointmentsForCompany(@PathVariable Long id){
        Set<Appointment> appointments = appointmentService.getByCompany(id);
        Set<AppointmentDTO> appointmentDTOS = (Set<AppointmentDTO>) new DtoUtils().convertToDtos(appointments, new AppointmentDTO());

        return new ResponseEntity<>(appointmentDTOS, HttpStatus.OK);
    }

}
