package medequipsystem.service;

import medequipsystem.domain.Appointment;
import medequipsystem.domain.Company;
import medequipsystem.domain.CompanyAdmin;
import medequipsystem.domain.enums.AppointmentStatus;
import medequipsystem.dto.ReservedAppointmentDTO;
import medequipsystem.repository.AppointmentRepository;
import medequipsystem.repository.CompanyAdminRepository;
import medequipsystem.util.TimeSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private CompanyAdminRepository adminRepository;
    private int duration = 30;

    @Transactional(readOnly = false)
    public Set<Appointment> getByCompany(Long id) {
        return appointmentRepository.getByCompanyId(id);
    }

    public Appointment getById(Long id) {
        Optional<Appointment> appointmentOptional = this.appointmentRepository.findById(id);
        return appointmentOptional.orElse(null);
    }

    @Transactional(readOnly = false) //kreiranje nove
    public Appointment createAppointment(Appointment a) throws Exception {
        try {
            Set<Appointment> appointmentsForCompany = appointmentRepository.getByCompanyId(a.getCompanyAdmin().getCompany().getId());
            Set<Appointment> appointmentsForDate = appointmentsForCompany.stream().filter(
                    appointment -> appointment.getDate().equals(a.getDate())
            ).collect(Collectors.toSet());

            Set<LocalTime> takenTimeSlots = generateTakenTimeSlots(appointmentsForDate);
            Set<LocalTime> requiredTimeSlots = generateTimeSlotsInRange(a.getStartTime(), a.getEndTime());
            requiredTimeSlots.retainAll(takenTimeSlots);

            if (requiredTimeSlots.isEmpty() && inWorkingHours(a)
                    && a.getStartTime().isBefore(a.getEndTime())
                    && a.getDate().isAfter(LocalDate.now())
                    && (!a.getDate().equals(LocalDate.now()) || a.getStartTime().isBefore(LocalTime.now()))
            )
                return appointmentRepository.save(a);
            return null;
        } catch (Exception e) {
            throw new Exception("Service is unable to save your appointment. Please try again.");
        }
    }

    public Set<Appointment> getCustomAppointments(Company company, LocalDate date) {
        Long idGenerator = 0L;
        var splitHours = company.getWorkingHours().split("-");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
        LocalTime startTime = LocalTime.parse(splitHours[0], formatter);
        LocalTime endTime = LocalTime.parse(splitHours[1], formatter);

        Set<Appointment> reservedAppointments = appointmentRepository.getByCompanyIdAndDate(company.getId(), date, AppointmentStatus.RESERVED);
        Set<TimeSlot> allTimeSlots = TimeSlot.generateTimeSlots(startTime, endTime);
        Set<Appointment> customAppointments = new HashSet<>();

        for (TimeSlot timeSlot : allTimeSlots) {
            List<Long> reservedAdminIds = timeSlot.isTimeSlotReserved(reservedAppointments);

            if (reservedAdminIds.isEmpty()) {
                customAppointments.add(new Appointment(idGenerator++, date, timeSlot.startTime, timeSlot.endTime, AppointmentStatus.AVAILABLE, null));
                continue;
            }
            // If there are reserved admins, check for available admins
            Set<Long> allAdminIds = company.getCompanyAdmins().stream().map(CompanyAdmin::getId).collect(Collectors.toSet());
            Set<Long> availableAdminIds = new HashSet<>(allAdminIds);
            availableAdminIds.removeAll(reservedAdminIds);

            // If there are available admins, add an available appointment
            if (!availableAdminIds.isEmpty()) {
                customAppointments.add(new Appointment(0L, date, timeSlot.startTime, timeSlot.endTime, AppointmentStatus.AVAILABLE, null));
            }

        }

        List<Appointment> sortedAppointments = new ArrayList<>(customAppointments);
        sortedAppointments.sort(Comparator.comparing(Appointment::getStartTime));
        Set<Appointment> sortedSet = new LinkedHashSet<>(sortedAppointments);
        return sortedSet;
    }

    @Transactional(readOnly = false)
    public Set<CompanyAdmin> isCustomAppoinmentAvailable(Company company, LocalDate date, LocalTime startTime) throws Exception {
        try {
            Set<Appointment> reservedAppointments = appointmentRepository.getByCompanyId(company.getId());
            Set<Appointment> appointmentsForDate = reservedAppointments.stream().filter(
                    appointment -> appointment.getDate().equals(date)).collect(Collectors.toSet());
            TimeSlot timeSlot = new TimeSlot(startTime);
            List<Long> reservedAdminIds = timeSlot.isTimeSlotReserved(reservedAppointments);

            if (reservedAdminIds.isEmpty()) {
                return company.getCompanyAdmins();
            } else {
                Set<Long> availableAdminIds = company.getCompanyAdmins().stream()
                        .map(CompanyAdmin::getId)
                        .filter(adminId -> !reservedAdminIds.contains(adminId))
                        .collect(Collectors.toSet());

                return company.getCompanyAdmins().stream()
                        .filter(admin -> availableAdminIds.contains(admin.getId()))
                        .collect(Collectors.toSet());
            }
        } catch (Exception e) {
            throw new Exception("Appointment is not available anymore", e.getCause());
        }
    }

    public boolean inWorkingHours(Appointment a) {
        Company c = adminRepository.getById(a.getCompanyAdmin().getId()).getCompany();

        var splitHours = c.getWorkingHours().split("-");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");

        LocalTime startTime = LocalTime.parse(splitHours[0], formatter);
        LocalTime endTime = LocalTime.parse(splitHours[1], formatter);
        return a.getStartTime().isAfter(startTime) && a.getEndTime().isBefore(endTime);

    }

    public Set<LocalTime> generateTimeSlotsInRange(LocalTime start, LocalTime end) {
        Set<LocalTime> timeSlots = new TreeSet<>();
        for (LocalTime it = start; !it.isAfter(end); it = it.plusMinutes(1)) {
            timeSlots.add(LocalTime.of(it.getHour(), it.getMinute()));

        }
        return timeSlots;
    }

    public Set<LocalTime> generateTakenTimeSlots(Set<Appointment> appointments) {
        Set<LocalTime> timeSlots = new HashSet<>();
        for (Appointment a : appointments) {
            LocalTime start = a.getStartTime();
            LocalTime end = a.getEndTime();
            for (LocalTime it = start; !it.isAfter(end); it = it.plusMinutes(1)) {
                timeSlots.add(LocalTime.of(it.getHour(), it.getMinute()));

            }
        }
        return timeSlots;
    }

    public Set<ReservedAppointmentDTO> getReservedAppointmentsByCompanyId(Long companyId) {
        return appointmentRepository.getReservedAppointmentsByCompanyId(companyId);
    }

    public Set<Appointment> getNotReservedAppointments(Long companyId) {
        return appointmentRepository.getNotReservedAppointments(companyId);
    }
}
