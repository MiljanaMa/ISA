package medequipsystem.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import medequipsystem.domain.Appointment;
import medequipsystem.domain.Company;
import medequipsystem.dto.ReservedAppointmentDTO;
import medequipsystem.repository.AppointmentRepository;

import medequipsystem.repository.CompanyAdminRepository;
import medequipsystem.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private CompanyAdminRepository adminRepository;

    public Set<Appointment> getByCompany(Long id){
        return appointmentRepository.getByCompanyId(id);
    }

    public Appointment createAppointment(Appointment a){
        Set<Appointment> appointmentsForDate = appointmentRepository.getByDate(a.getDate());
        Set<LocalTime> takenTimeSlots = generateTakenTimeSlots(appointmentsForDate);
        Set<LocalTime> requiredTimeSlots = generateTimeSlotsInRange(a.getStartTime(), a.getEndTime());
        requiredTimeSlots.retainAll(takenTimeSlots);
        if(requiredTimeSlots.isEmpty() && inWorkingHours(a)
                && a.getStartTime().isBefore(a.getEndTime())
                && a.getDate().isAfter(LocalDate.now())
                && (!a.getDate().equals(LocalDate.now()) || a.getStartTime().isBefore(LocalTime.now()))
        ){
            return appointmentRepository.save(a);
        }
        return null;
    }

    public boolean inWorkingHours(Appointment a){
        Company c  = adminRepository.getById(a.getCompanyAdmin().getId()).getCompany();

        var splitHours = c.getWorkingHours().split("-");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");

        LocalTime startTime  = LocalTime.parse(splitHours[0], formatter);
        LocalTime endTime = LocalTime.parse(splitHours[1], formatter);
        return a.getStartTime().isAfter(startTime) && a.getEndTime().isBefore(endTime);

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

    public Set<ReservedAppointmentDTO> getReservedAppointmentsByCompanyId(Long companyId) {
        return appointmentRepository.getReservedAppointmentsByCompanyId(companyId);
    }
}
