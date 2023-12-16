package medequipsystem.controller;

import medequipsystem.domain.*;
import medequipsystem.domain.enums.ReservationStatus;
import medequipsystem.dto.*;
import medequipsystem.mapper.GenericMapper;
import medequipsystem.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;


@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping(value = "api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;
    //vidi da li su potrebni ovi maperi
    @Autowired
    private GenericMapper<Reservation,ReservationDTO> reservationMapper;
    @Autowired
    private GenericMapper<ReservationItem, ReservationItemDTO> reservationItemMapper;
    @Autowired
    private GenericMapper<CompanyEquipment, CompanyEquipmentDTO> companyEquipmentMapper;

    @Autowired
    private GenericMapper<Client, ClientDTO> userMapper;
    @Autowired
    private GenericMapper<Appointment, AppointmentDTO> appointmentMapper;

    @PostMapping(value = "/create")
    public void create(@RequestBody ReservationCreationDTO reservationDTO) {
        //trbea mi citav appointment kao custom ili kao citav koji salje sa backa

        //potencijalno ce da radi jer nema dva nivoa
        Set<ReservationItem> reservationItems = new HashSet<>();
        CompanyEquipment ce;
        ReservationItem ri1;
        for(ReservationItemDTO ri: reservationDTO.getReservationItems()){
            ce = companyEquipmentMapper.toModel(ri.getEquipment());
            ri1 = reservationItemMapper.toModel(ri);
            ri1.setCount(ri.getCount());
            ri1.setEquipment(ce);
            reservationItems.add(ri1);

        }
        //User user = userMapper.toModel(reservationDTO.getClient());
        Appointment appointment = appointmentMapper.toModel(reservationDTO.getAppointment());
        //potencijalno proslijedi usera, da ne pravis rezervaciju, takodje i provjera za appointment
        Reservation savedReservation = reservationService.create( appointment, reservationItems, 1L);

        //ReservationDTO dto = reservationMapper.toDto(savedReservation);
        return ;
    }

}
