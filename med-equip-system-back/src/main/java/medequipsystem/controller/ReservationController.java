package medequipsystem.controller;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import medequipsystem.domain.*;
import medequipsystem.domain.enums.ReservationStatus;
import medequipsystem.dto.*;
import medequipsystem.mapper.MapperUtils.DtoUtils;
import medequipsystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
            return ResponseEntity.badRequest().body("{\"message\":You are not authorized}");
        Client client = clientService.getByUserId(reservationUser.getId());
        if(client == null)
            return ResponseEntity.badRequest().body("{\"message\":You are not authorized}");
        else if(client.getPenaltyPoints() >= 3)
            return ResponseEntity.badRequest().body("{\"message\":You can't make reservation because of penalty points}");
        CompanyEquipment ce;
        for(ReservationItemDTO reservationItemDTO: reservationDTO.getReservationItems()){
            ce = equipmentService.getById(reservationItemDTO.getEquipment().getId());
            if(ce.getCount()-ce.getReservedCount() < reservationItemDTO.getCount()){
                return ResponseEntity.badRequest().body("{\"message\":There is not enough items in storage.}");
            }
        }

        CustomAppointmentDTO appointment =  reservationDTO.getAppointment();
        Set<ReservationItem> reservationItems =  (Set<ReservationItem>) new DtoUtils().convertToEntities(new ReservationItem(), reservationDTO.getReservationItems());

        Company company = companyService.getById(reservationDTO.getCompanyId());
        Set<CompanyAdmin> availableAdmins = appointmentService.isCustomAppoinmentAvailable(company,appointment.getDate(), appointment.getStartTime());
        if(availableAdmins.isEmpty())
            return ResponseEntity.badRequest().body("{\"message\":Appointment is not available anymore}");
        try {
            Reservation savedReservation = reservationService.createCustom(appointment, reservationItems, client, availableAdmins);
        /*if(savedReservation == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        */
            emailService.sendReservationMail(user.getName(), reservationService.getQrCode(savedReservation));
            return ResponseEntity.ok().body("{\"message\": \"You have successfully made a reservation\"}");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\":An error occurred during reservation creation}");
        }
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

    @PostMapping(value = "/take")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> take(@RequestBody Long reservationId, Principal user) {
        Long userId = userService.getByEmail(user.getName()).getId();
        Long clientId = clientService.getByUserId(userId).getId();
        Client client = this.clientService.getById(clientId);
        Reservation reservation = this.reservationService.getById(reservationId);

        if(reservation.status != ReservationStatus.RESERVED)
            return ResponseEntity.badRequest().body("{\"message\": \"Selected reservation's status is not RESERVED.\"}");

        if(this.reservationService.isReservationExpired(reservation.getAppointment().getDate(), reservation.getAppointment().getEndTime())){
            this.reservationService.updateStatus(reservation, ReservationStatus.EXPIRED);
            this.clientService.penalize(client, 2);
            return ResponseEntity.ok().body("{\"message\": \"Your reservation has expired. You have received 2 penalty points.\"}");
        }

        //  NAPOMENA za Prasku:

        //  treba dodati negde da admin oznacava da je preuzeta oprema, verovatno da poziva ovu metodu,
        // a da klijent kada zeli da preuzme opremu posalje neki zahtev za pruzimanje... Vidi kako ti odgovara, a ja cu na frontu
        // za sada pozivati ovu metodu na frontu, pa ako budes pravio nesto preko zahteva javi da ispravim,
        // ili promeni i moje, s obzirom da cemo pozivati iste metode

        //  ova metoda radi sve vezano za opremu, pa je mozes pozvati kod sebe, samo eto vidi to za admina, jer mislim da bi on mozda
        //  trebao da ovu poziva...

        this.reservationService.take(reservationId);
        //TODO: posalji mejl
        emailService.sendEquipmentPickupConfirmationMail(user.getName(), reservation);

        return ResponseEntity.ok().body("{\"message\": \"You have successfully taken your reservation\"}");
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/uploadQRCode")
    public ResponseEntity<String> uploadQRCode(@RequestParam("file") MultipartFile file) {
        try {
            String reservationDetails = this.reservationService.processQRCode(file);
            Long reservationId = this.reservationService.extractReservationId(reservationDetails);
            return ResponseEntity.ok().body("{\"message\": \"You have successfully uploaded QR code\", \"reservationId\": " + reservationId + "}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}
