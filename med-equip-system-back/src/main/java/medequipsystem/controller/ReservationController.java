package medequipsystem.controller;

import medequipsystem.domain.*;
import medequipsystem.domain.enums.ReservationStatus;
import medequipsystem.dto.*;
import medequipsystem.mapper.GenericMapper;
import medequipsystem.mapper.MapperUtils.DtoUtils;
import medequipsystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;


@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping(value = "api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;
    @Autowired
    private ClientService clientService;

    @PostMapping(value = "/create/predefined")
    @PreAuthorize("hasAnyRole('CLIENT')")
    public ResponseEntity<String> createPredefined(@RequestBody ReservationCreationDTO reservationDTO, Principal user) {
        //maybe change that client has only one id connected to user and doesnt have its own id(same for sysadmin, compadmin)
        User reservationUser = userService.getByEmail(user.getName());
        if(reservationUser == null)
            return ResponseEntity.badRequest().body("You are not authorized");
        Client client = clientService.getByUserId(reservationUser.getId());
        if(client == null)
            return ResponseEntity.badRequest().body("You are not authorized");

        Appointment appointment =  (Appointment) new DtoUtils().convertToEntity(new Appointment(), reservationDTO.getAppointment());
        Set<ReservationItem> reservationItems =  (Set<ReservationItem>) new DtoUtils().convertToEntities(new ReservationItem(), reservationDTO.getReservationItems());
        Reservation savedReservation = reservationService.createPredefined( appointment, reservationItems, client);
        /*if(savedReservation == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        */

        emailService.sendReservationMail(user.getName(), savedReservation);

        //ReservationDTO dto = reservationMapper.toDto(savedReservation);
        return ResponseEntity.ok().body("You have successfully made reservation");
    }
    @PostMapping(value = "/create/custom")
    @PreAuthorize("hasAnyRole('CLIENT')")
    public ResponseEntity<String> createCustom(@RequestBody CustomReservationDTO reservationDTO, Principal user) {
        //maybe change that client has only one id connected to user and doesnt have its own id(same for sysadmin, compadmin)
        User reservationUser = userService.getByEmail(user.getName());
        if(reservationUser == null)
            return ResponseEntity.badRequest().body("You are not authorized");
        Client client = clientService.getByUserId(reservationUser.getId());
        if(client == null)
            return ResponseEntity.badRequest().body("You are not authorized");

        CustomAppointmentDTO appointment =  reservationDTO.getAppointment();
        Set<ReservationItem> reservationItems =  (Set<ReservationItem>) new DtoUtils().convertToEntities(new ReservationItem(), reservationDTO.getReservationItems());

        Company company = companyService.getById(reservationDTO.getCompanyId());
        Set<CompanyAdmin> availableAdmins = appointmentService.isCustomAppoinmentAvailable(company,appointment.getDate(), appointment.getStartTime());
        if(availableAdmins.isEmpty())
            return ResponseEntity.badRequest().body("Appointment is not available anymore");
        try {
        Reservation savedReservation = reservationService.createCustom(appointment, reservationItems, client, availableAdmins);
        /*if(savedReservation == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        */

        emailService.sendReservationMail(user.getName(), savedReservation);

        //ReservationDTO dto = reservationMapper.toDto(savedReservation);
        return ResponseEntity.ok().body("{\"message\": \"You have successfully made a reservation\"}");
        } catch (Exception e) {
            // Log the exception
            // logger.error("Error creating reservation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during reservation creation");
        }
    }
    @GetMapping(value = "/user")
    @PreAuthorize("hasAnyRole('CLIENT')")
    public ResponseEntity<Set<ReservationDTO>> getAppointmentsForCompany(Principal user){
        User reservationUser = userService.getByEmail(user.getName());

        Set<Reservation> reservations = reservationService.getUserReservations(reservationUser.getId());
        Set<ReservationDTO> reservationDTOS = (Set<ReservationDTO>) new DtoUtils().convertToDtos(reservations, new ReservationDTO());

        return new ResponseEntity<>(reservationDTOS, HttpStatus.OK);
    }

}
