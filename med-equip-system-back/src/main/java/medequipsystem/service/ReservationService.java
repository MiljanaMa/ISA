package medequipsystem.service;

import medequipsystem.domain.Appointment;
import medequipsystem.domain.Reservation;
import medequipsystem.domain.ReservationItem;
import medequipsystem.domain.User;
import medequipsystem.domain.enums.ReservationStatus;
import medequipsystem.repository.AppointmentRepository;
import medequipsystem.repository.ReservationRepository;
import medequipsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;


@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;


    public Reservation create(Appointment appointment, Set<ReservationItem> reservationItems, Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        //ako je appointment id null onda provjeri je li okej termin, ako je termin koji se dobije reserved throuw exception
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointment.getId());
        //if( appointmentOptional == null)
        Reservation reservation = new Reservation(0L, ReservationStatus.RESERVED, userOptional.orElse(null), appointment, reservationItems);
        //da li je potreban korak
        /*for(ReservationItem ri: reservation.getReservationItems())
            reservation.addReservationItem(ri);*/
        return reservationRepository.save(reservation);
    }
}
