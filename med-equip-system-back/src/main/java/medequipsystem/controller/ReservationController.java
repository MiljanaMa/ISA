package medequipsystem.controller;

import medequipsystem.domain.*;
import medequipsystem.dto.*;
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
    private CompanyEquipmentService equipmentService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;
    @Autowired
    private ClientService clientService;
    //maybe change that client has only one id connected to user and doesnt have its own id(same for sysadmin, compadmin)
    @PostMapping(value = "/create/predefined")
    @PreAuthorize("hasAnyRole('CLIENT')")
    public ResponseEntity<String> createPredefined(@RequestBody ReservationCreationDTO reservationDTO, Principal user) {
        try {
            Client client = clientService.getLogged(user);
            Appointment appointment = (Appointment) new DtoUtils().convertToEntity(new Appointment(), reservationDTO.getAppointment());
            Set<ReservationItem> reservationItems = (Set<ReservationItem>) new DtoUtils().convertToEntities(new ReservationItem(), reservationDTO.getReservationItems());
            Reservation createdReservation = reservationService.createPredefined(appointment, reservationItems, client);
            emailService.sendReservationMail(user.getName(), reservationService.getQrCode(createdReservation));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"message\":\"" + e.getMessage() + "\"}");
        }
        return ResponseEntity.ok().body("{\"message\": \"You have successfully made a reservation\"}");
    }

    @PostMapping(value = "/create/custom")
    @PreAuthorize("hasAnyRole('CLIENT')")
    public ResponseEntity<String> createCustom(@RequestBody ReservationCreationDTO reservationDTO, Principal user) {
        try {
            Client client = clientService.getLogged(user);
            Appointment appointment = (Appointment) new DtoUtils().convertToEntity(new Appointment(), reservationDTO.getAppointment());
            Set<ReservationItem> reservationItems = (Set<ReservationItem>) new DtoUtils().convertToEntities(new ReservationItem(), reservationDTO.getReservationItems());
            Reservation createdReservation = reservationService.createCustom(appointment, reservationItems, client);
            emailService.sendReservationMail(user.getName(), reservationService.getQrCode(createdReservation));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"message\":\"" + e.getMessage() + "\"}");
        }
        return ResponseEntity.ok().body("{\"message\": \"You have successfully made a reservation\"}");
    }

    @GetMapping(value = "/user")
    @PreAuthorize("hasAnyRole('CLIENT')")
    public ResponseEntity<Set<ReservationDTO>> getUserReservations(Principal user) {
        User reservationUser = userService.getByEmail(user.getName());

        Set<Reservation> reservations = reservationService.getUserReservations(reservationUser.getId());
        Set<ReservationDTO> reservationDTOS = (Set<ReservationDTO>) new DtoUtils().convertToDtos(reservations, new ReservationDTO());

        return new ResponseEntity<>(reservationDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/qrCodes")
    @PreAuthorize("hasAnyRole('CLIENT')")
    public ResponseEntity<Set<QRCodeDTO>> getQRCodes(Principal user) {
        User reservationUser = userService.getByEmail(user.getName());
        Set<QRCodeDTO> qrCodes = new HashSet<>();
        for (Reservation r : reservationService.getUserReservations(reservationUser.getId()))
            qrCodes.add(new QRCodeDTO(reservationService.getQrCode(r), r.status));

        return new ResponseEntity<>(qrCodes, HttpStatus.OK);
    }

}
