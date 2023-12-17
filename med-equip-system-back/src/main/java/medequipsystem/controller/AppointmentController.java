package medequipsystem.controller;

import medequipsystem.domain.Appointment;
import medequipsystem.domain.Company;
import medequipsystem.dto.AppointmentDTO;
import medequipsystem.dto.CustomAppointmentDTO;
import medequipsystem.mapper.MapperUtils.DtoUtils;
import medequipsystem.service.AppointmentService;
import medequipsystem.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping(value = "api/appointments")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;
    @Autowired
    CompanyService companyService;
    @PostMapping(value = "/create")
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO appointmentDTO){
        Appointment appointment = (Appointment) new DtoUtils().convertToEntity(new Appointment(), appointmentDTO);
        Appointment result = appointmentService.createAppointment(appointment);
        AppointmentDTO dto = (AppointmentDTO) new DtoUtils().convertToDto(result, new AppointmentDTO());
        return new ResponseEntity<>(dto , HttpStatus.OK);
    }

    @GetMapping(value = "/company/{id}")
    public ResponseEntity<Set<AppointmentDTO>> getAppointmentsForCompany(@PathVariable Long id){
        Set<Appointment> appointments = appointmentService.getByCompany(id);
        Set<AppointmentDTO> appointmentDTOS = (Set<AppointmentDTO>) new DtoUtils().convertToDtos(appointments, new AppointmentDTO());

        return new ResponseEntity<>(appointmentDTOS, HttpStatus.OK);
    }
    @GetMapping(value = "/custom")
    @PreAuthorize("hasAnyRole('CLIENT')")
    public ResponseEntity<Set<CustomAppointmentDTO>> getCustomAppointments(@RequestParam String date, @RequestParam Long companyId, Principal user){
        Company company = companyService.getById(companyId);
        if(company == null)
            return ResponseEntity.notFound().build();
        Set<CustomAppointmentDTO> customAppointments = appointmentService.getCustomAppointments(company, LocalDate.parse(date));
        return new ResponseEntity<>(customAppointments, HttpStatus.OK);
    }
}
