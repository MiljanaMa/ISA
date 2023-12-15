package medequipsystem.service;

import medequipsystem.domain.Appointment;
import medequipsystem.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Set<Appointment> getByCompany(Long id){
        return appointmentRepository.getByCompanyId(id);
    }

    public Set<Appointment> createAppointment(Appointment a){
        Set<Appointment> appointmentsForDate = appointmentRepository.getByDate(a.getDate());


        return appointmentsForDate;
    }

    public Set<LocalTime> generateTakenTimeSlots(Set<Appointment> appointments){
        Set<LocalTime> takenTimeSlots = new HashSet<LocalTime>();
        for(Appointment a: appointments){
            LocalTime start = a.getStartTime();
            LocalTime end = a.getEndTime();
            for(LocalTime iter = start; !iter.isAfter(end); iter = iter.plusMinutes(1)){
                //takenTimeSlots.add();

            }
        }
        return takenTimeSlots;
    }

}
