package medequipsystem.service;

import medequipsystem.domain.Appointment;
import medequipsystem.repository.AppointmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Appointment createAppointment(Appointment a){
        Set<Appointment> appointmentsForDate = appointmentRepository.getByDate(a.getDate());
        Set<LocalTime> takenTimeSlots = generateTakenTimeSlots(appointmentsForDate);
        Set<LocalTime> requiredTimeSlots = generateTimeSlotsInRange(a.getStartTime(), a.getEndTime());
        requiredTimeSlots.retainAll(takenTimeSlots);

        if(requiredTimeSlots.isEmpty()){
            appointmentRepository.save(a);
            return a;
        }
        return null;
    }

    public Set<LocalTime> generateTimeSlotsInRange(LocalTime start, LocalTime end){
        Set<LocalTime> timeSlots = new HashSet<>();
        for(LocalTime it = start; !it.isAfter(end); it = it.plusMinutes(1)){
            timeSlots.add(LocalTime.of(it.getHour(), it.getMinute()));

        }
        return timeSlots;
    }
    public Set<LocalTime> generateTakenTimeSlots(Set<Appointment> appointments){
        Set<LocalTime> timeSlots = new HashSet<>();
        for(Appointment a: appointments){
            LocalTime start = a.getStartTime();
            LocalTime end = a.getEndTime();
            for(LocalTime it = start; !it.isAfter(end); it = it.plusMinutes(1)){
                timeSlots.add(LocalTime.of(it.getHour(), it.getMinute()));

            }
        }
        return timeSlots;
    }

}
