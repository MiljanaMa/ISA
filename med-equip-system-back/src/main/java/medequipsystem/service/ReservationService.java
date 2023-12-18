package medequipsystem.service;

import medequipsystem.domain.*;
import medequipsystem.domain.enums.AppointmentStatus;
import medequipsystem.domain.enums.ReservationStatus;
import medequipsystem.dto.CustomAppointmentDTO;
import medequipsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private CompanyEquipmentRepository equipmentRepository;


    public Reservation createPredefined(Appointment appointment, Set<ReservationItem> reservationItems, Client client){
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointment.getId());
        if( appointmentOptional == null)
            return null;
        Reservation reservation = new Reservation(0L, ReservationStatus.RESERVED, client, appointment, reservationItems);
        //korak za smanjivanje kolicine robe
        CompanyEquipment ce;
        Set<ReservationItem> changedItems = new HashSet<>();
        for(ReservationItem ri: reservation.getReservationItems()){
            ce = equipmentRepository.getReferenceById(ri.getEquipment().getId());
            if(ri.getCount() > (ce.getCount() - ce.getReservedCount()))
                return null;
            ce.setReservedCount(ce.getReservedCount() + ri.getCount());
            changedItems.add(new ReservationItem(ri.getId(), ri.getCount(), ce));
        }
        reservation.setReservationItems(changedItems);

        Appointment existingAppointment = appointmentOptional.get();
        existingAppointment.setStatus(AppointmentStatus.RESERVED);
        appointmentRepository.save(existingAppointment);

        return reservationRepository.save(reservation);
    }


    public Set<Reservation> getReservationsInProgress(){

        return reservationRepository.getReservationsInProgress();
    }

    public Reservation createCustom(CustomAppointmentDTO appointment, Set<ReservationItem> reservationItems, Client client, Set<CompanyAdmin> admins){
        Reservation reservation = new Reservation(0L, ReservationStatus.RESERVED, client, new Appointment(), reservationItems);
        //korak za smanjivanje kolicine robe
        CompanyEquipment ce;
        Set<ReservationItem> changedItems = new HashSet<>();
        for(ReservationItem ri: reservation.getReservationItems()){
            ce = equipmentRepository.getReferenceById(ri.getEquipment().getId());
            if(ri.getCount() > (ce.getCount() - ce.getReservedCount()))
                return null;
            ce.setReservedCount(ce.getReservedCount() + ri.getCount());
            changedItems.add(new ReservationItem(ri.getId(), ri.getCount(), ce));
        }
        reservation.setReservationItems(changedItems);

        Appointment newAppointment = new Appointment(0L, appointment.getDate(), appointment.getStartTime(), appointment.getEndTime(), AppointmentStatus.RESERVED, admins.stream().findFirst().get());
        Appointment savedAppointment = appointmentRepository.save(newAppointment);
        reservation.setAppointment(savedAppointment);
        return reservationRepository.save(reservation);
    }
    public Set<Reservation> getUserReservations(Long id){
        //stavi da sortira
        List<Reservation> reservations = reservationRepository.findAll();
        Set<Reservation> userReservations = reservations.stream()
                .filter(reservation -> reservation.getClient().getUser().getId().equals(id))
                .collect(Collectors.toSet());
        return userReservations;
    }
}
