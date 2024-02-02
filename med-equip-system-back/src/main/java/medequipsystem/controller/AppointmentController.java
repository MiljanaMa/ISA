package medequipsystem.controller;

import io.jsonwebtoken.ExpiredJwtException;
import medequipsystem.domain.Appointment;
import medequipsystem.domain.Company;
import medequipsystem.dto.AppointmentDTO;
import medequipsystem.dto.ReservedAppointmentDTO;
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
import java.util.*;

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
        try{
            Appointment appointment = (Appointment) new DtoUtils().convertToEntity(new Appointment(), appointmentDTO);
            Appointment result = appointmentService.createAppointment(appointment);
            AppointmentDTO dto = (AppointmentDTO) new DtoUtils().convertToDto(result, new AppointmentDTO());
            return new ResponseEntity<>(dto , HttpStatus.OK);

        }catch(Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(value = "/company/{id}")
    public ResponseEntity<Set<AppointmentDTO>> getAppointmentsForCompany(@PathVariable Long id) throws Exception {
        Set<Appointment> appointments = appointmentService.getByCompany(id);
        Set<AppointmentDTO> appointmentDTOS = (Set<AppointmentDTO>) new DtoUtils().convertToDtos(appointments, new AppointmentDTO());

        return new ResponseEntity<>(appointmentDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('COMPADMIN')")
    @GetMapping(value = "/companycalendar/{companyId}") //TODO: bolja imena za putanje
    public ResponseEntity<Set<ReservedAppointmentDTO>> getReservedAppointmentsByCompanyId(@PathVariable Long companyId) {
        Set<ReservedAppointmentDTO> reservedAppointmentDTOS = appointmentService.getReservedAppointmentsByCompanyId(companyId);
        return new ResponseEntity<>(reservedAppointmentDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/notreservedappointments/{companyId}") //TODO: bolja imena, nemam snage trenutno
    public ResponseEntity<Set<AppointmentDTO>> getNotReservedAppointments(@PathVariable Long companyId){
        Set<Appointment> appointments = appointmentService.getNotReservedAppointments(companyId);
        Set<AppointmentDTO> appointmentDTOS = (Set<AppointmentDTO>) new DtoUtils().convertToDtos(appointments, new AppointmentDTO());
        return new ResponseEntity<>(appointmentDTOS, HttpStatus.OK);
    }


    @GetMapping(value = "/custom")
    @PreAuthorize("hasAnyRole('CLIENT')")
    public ResponseEntity<Set<AppointmentDTO>> getCustomAppointments(@RequestParam String date, @RequestParam Long companyId, Principal user){
        Company company = companyService.getById(companyId);
        if(company == null)
            return ResponseEntity.notFound().build();
        Set<Appointment> customAppointments = appointmentService.getCustomAppointments(company, LocalDate.parse(date));
        Set<AppointmentDTO> customAppointmentsDTO = (Set<AppointmentDTO>)new DtoUtils().convertToDtos(customAppointments, new AppointmentDTO());
        Set<AppointmentDTO> sortedAppointments = new TreeSet<>(Comparator.comparing(AppointmentDTO::getStartTime));
        sortedAppointments.addAll(customAppointmentsDTO);
        return new ResponseEntity<>(sortedAppointments, HttpStatus.OK);
    }
}
