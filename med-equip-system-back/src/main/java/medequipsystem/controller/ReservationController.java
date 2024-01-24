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

    @PostMapping(value = "/create/predefined")
    @PreAuthorize("hasAnyRole('CLIENT')")
    public ResponseEntity<String> createPredefined(@RequestBody ReservationCreationDTO reservationDTO, Principal user) {
        //maybe change that client has only one id connected to user and doesnt have its own id(same for sysadmin, compadmin)
        User reservationUser = userService.getByEmail(user.getName());
        if(reservationUser == null)
            return ResponseEntity.badRequest().body("{\"message\":You are not authorized}");
        Client client = clientService.getByUserId(reservationUser.getId());
        if(client == null)
            return ResponseEntity.badRequest().body("{\"message\":You are not authorized}");
        else if(client.getPenaltyPoints() >= 3)
            return ResponseEntity.badRequest().body("{\"message\":You can't make reservation because of penalty points}");
        /*REFACTOR THIS - and for some reason it always gets 0 for reservedCount, I don't know why*/
        CompanyEquipment ce;
        for(ReservationItemDTO reservationItemDTO: reservationDTO.getReservationItems()){
            ce = equipmentService.getById(reservationItemDTO.getEquipment().getId());
            if(ce.getCount()-ce.getReservedCount() < reservationItemDTO.getCount()){
                return ResponseEntity.badRequest().body("{\"message\": \"There is not enough items in storage.\"}");
            }
        }


        Appointment appointment =  (Appointment) new DtoUtils().convertToEntity(new Appointment(), reservationDTO.getAppointment());
        Set<ReservationItem> reservationItems =  (Set<ReservationItem>) new DtoUtils().convertToEntities(new ReservationItem(), reservationDTO.getReservationItems());
        Reservation savedReservation = reservationService.createPredefined( appointment, reservationItems, client);
        /*if(savedReservation == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        */

        emailService.sendReservationMail(user.getName(), reservationService.getQrCode(savedReservation));

        return ResponseEntity.ok().body("{\"message\": \"You have successfully made a reservation\"}");
    }
    @PostMapping(value = "/create/custom")
    @PreAuthorize("hasAnyRole('CLIENT')")
    public ResponseEntity<String> createCustom(@RequestBody CustomReservationDTO reservationDTO, Principal user) {
        //maybe change that client has only one id connected to user and doesnt have its own id(same for sysadmin, compadmin)
        User reservationUser = userService.getByEmail(user.getName());
        if(reservationUser == null)
            return ResponseEntity.badRequest().body("{\"message\":\"You are not authorized\"}");
        Client client = clientService.getByUserId(reservationUser.getId());
        if(client == null)
            return ResponseEntity.badRequest().body("{\"message\":\"You are not authorized\"}");
        else if(client.getPenaltyPoints() >= 3)
            return ResponseEntity.badRequest().body("{\"message\":\"You can't make reservation because of penalty points\"}");

        CompanyEquipment ce;
        for(ReservationItemDTO reservationItemDTO: reservationDTO.getReservationItems()){
            ce = equipmentService.getById(reservationItemDTO.getEquipment().getId());
            if(ce.getCount()-ce.getReservedCount() < reservationItemDTO.getCount()){
                return ResponseEntity.badRequest().body("{\"message\":\"There is not enough items in storage.\"}");
            }
        }

        CustomAppointmentDTO appointment =  reservationDTO.getAppointment();
        Set<ReservationItem> reservationItems =  (Set<ReservationItem>) new DtoUtils().convertToEntities(new ReservationItem(), reservationDTO.getReservationItems());

        Company company = companyService.getById(reservationDTO.getCompanyId());
        Set<CompanyAdmin> availableAdmins = appointmentService.isCustomAppoinmentAvailable(company,appointment.getDate(), appointment.getStartTime());
        if(availableAdmins.isEmpty())
            return ResponseEntity.badRequest().body("{\"message\":\"Appointment is not available anymore\"}");
        try {
            Reservation savedReservation = reservationService.createCustom(appointment, reservationItems, client, availableAdmins);
        /*if(savedReservation == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        */
            emailService.sendReservationMail(user.getName(), reservationService.getQrCode(savedReservation));
            return ResponseEntity.ok().body("{\"message\": \"You have successfully made a reservation\"}");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\":\"An error occurred during reservation creation\"}");
        }
    }

    @PostMapping(value = "/cancel")
    @PreAuthorize("hasAnyRole('CLIENT')")
    public ResponseEntity<String> cancel(@RequestBody ReservationDTO reservationDTO, Principal curentUser) {
        this.reservationService.cancel(reservationDTO.getId());
        User user = userService.getByEmail(curentUser.getName());
        Long clientId = clientService.getByUserId(user.getId()).getId(); //change this, clientId will not exist, just userId
        this.clientService.penalize(clientId, reservationDTO.getAppointment().getDate(), reservationDTO.getAppointment().getStartTime());
        return ResponseEntity.ok().body("{\"message\": \"You have successfully cancelled your reservation\"}");
    }

    @GetMapping(value = "/user")
    @PreAuthorize("hasAnyRole('CLIENT')")
    public ResponseEntity<Set<ReservationDTO>> getUserReservations(Principal user){
        User reservationUser = userService.getByEmail(user.getName());

        Set<Reservation> reservations = reservationService.getUserReservations(reservationUser.getId());
        Set<ReservationDTO> reservationDTOS = (Set<ReservationDTO>) new DtoUtils().convertToDtos(reservations, new ReservationDTO());

        return new ResponseEntity<>(reservationDTOS, HttpStatus.OK);
    }
    @GetMapping(value = "/qrCodes")
    @PreAuthorize("hasAnyRole('CLIENT')")
    public ResponseEntity<Set<QRCodeDTO>> getQRCodes(Principal user){
        User reservationUser = userService.getByEmail(user.getName());
        Set<QRCodeDTO> qrCodes = new HashSet<>();
        for(Reservation r: reservationService.getUserReservations(reservationUser.getId()))
            qrCodes.add(new QRCodeDTO(reservationService.getQrCode(r), r.status));

        return new ResponseEntity<>(qrCodes, HttpStatus.OK);
    }

}
