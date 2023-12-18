package medequipsystem.service;

import medequipsystem.domain.Appointment;
import medequipsystem.domain.Company;
import medequipsystem.dto.ReservedAppointmentDTO;
import medequipsystem.domain.CompanyAdmin;
import medequipsystem.domain.enums.AppointmentStatus;
import medequipsystem.dto.CustomAppointmentDTO;
import medequipsystem.repository.AppointmentRepository;
import medequipsystem.repository.CompanyAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Set<Appointment> getByCompany(Long id) {
        return appointmentRepository.getByCompanyId(id);
    }

    public Appointment getById(Long id) {
        Optional<Appointment> appointmentOptional = this.appointmentRepository.findById(id);
        return appointmentOptional.orElse(null);
    }

    public Appointment createAppointment(Appointment a) {
        Set<Appointment> appointmentsForDate = appointmentRepository.getByDate(a.getDate());
        Set<LocalTime> takenTimeSlots = generateTakenTimeSlots(appointmentsForDate);
        Set<LocalTime> requiredTimeSlots = generateTimeSlotsInRange(a.getStartTime(), a.getEndTime());
        requiredTimeSlots.retainAll(takenTimeSlots);
        if (requiredTimeSlots.isEmpty() && inWorkingHours(a)
                && a.getStartTime().isBefore(a.getEndTime())
                && a.getDate().isAfter(LocalDate.now())
                && (!a.getDate().equals(LocalDate.now()) || a.getStartTime().isBefore(LocalTime.now()))
        ) {
            return appointmentRepository.save(a);
        }
        return null;
    }

    public Set<CustomAppointmentDTO> getCustomAppointments(Company company, LocalDate date) {
        Long idGenerator = 0L;
        var splitHours = company.getWorkingHours().split("-");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
        LocalTime startTime = LocalTime.parse(splitHours[0], formatter);
        LocalTime endTime = LocalTime.parse(splitHours[1], formatter);

        Set<Appointment> reservedAppointments = appointmentRepository.getByCompanyIdAndDate(company.getId(), date, AppointmentStatus.RESERVED);
        Set<LocalTime> allTimeSlots = generateTimeSlotsInWorkingHours(startTime, endTime);
        Set<CustomAppointmentDTO> customAppointments = new HashSet<>();

        for (LocalTime timeSlot : allTimeSlots) {
            List<Long> reservedAdminIds = isTimeSlotReserved(reservedAppointments, timeSlot);
            //if there is no reserved add automatically
            if (reservedAdminIds.isEmpty()) {
                customAppointments.add(new CustomAppointmentDTO(idGenerator++, date, timeSlot, timeSlot.plusMinutes(duration), AppointmentStatus.AVAILABLE));
                continue;
            }
            // If there are reserved admins, check for available admins
            Set<Long> allAdminIds = company.getCompanyAdmins().stream().map(CompanyAdmin::getId).collect(Collectors.toSet());
            Set<Long> availableAdminIds = new HashSet<>(allAdminIds);
            availableAdminIds.removeAll(reservedAdminIds);

            // If there are available admins, add an available appointment
            if (!availableAdminIds.isEmpty()) {
                customAppointments.add(new CustomAppointmentDTO(0L, date, timeSlot, timeSlot.plusMinutes(duration), AppointmentStatus.AVAILABLE));
            }

        }

        List<CustomAppointmentDTO> sortedAppointments = new ArrayList<>(customAppointments);
        sortedAppointments.sort(Comparator.comparing(CustomAppointmentDTO::getStartTime));
        Set<CustomAppointmentDTO> sortedSet = new LinkedHashSet<>(sortedAppointments);
        return sortedSet;
    }

    public Set<CompanyAdmin> isCustomAppoinmentAvailable(Company company, LocalDate date, LocalTime timeSlot) {
        Set<Appointment> reservedAppointments = appointmentRepository.getByCompanyIdAndDate(company.getId(), date, AppointmentStatus.RESERVED);
        List<Long> reservedAdminIds = isTimeSlotReserved(reservedAppointments, timeSlot);
        if (reservedAdminIds.isEmpty())
            return company.getCompanyAdmins();

        Set<Long> allAdminIds = company.getCompanyAdmins().stream().map(CompanyAdmin::getId).collect(Collectors.toSet());
        Set<Long> availableAdminIds = new HashSet<>(allAdminIds);
        availableAdminIds.removeAll(reservedAdminIds);

        Set<CompanyAdmin> availableAdmins = company.getCompanyAdmins().stream()
                .filter(admin -> availableAdminIds.contains(admin.getId()))
                .collect(Collectors.toSet());

        // If there are available admins, add an available appointment
        if (!availableAdminIds.isEmpty())
            return availableAdmins;

        return new HashSet<CompanyAdmin>();
    }

    private List<Long> isTimeSlotReserved(Set<Appointment> reservedAppointments, LocalTime timeSlot) {
        List<Long> reservedAdminIds = new ArrayList<>();

        for (Appointment appointment : reservedAppointments) {
            if (isTimeSlotOverlap(timeSlot, appointment.getStartTime(), appointment.getEndTime())) {
                reservedAdminIds.add(appointment.getCompanyAdmin().getId());
            }
        }
        return reservedAdminIds;
    }

    private boolean isTimeSlotOverlap(LocalTime timeSlot, LocalTime startTime, LocalTime endTime) {
        // Check if two time slots overlap
        return !((timeSlot.plusMinutes(duration).isBefore(startTime) || timeSlot.plusMinutes(duration).equals(startTime))
                || (timeSlot.isAfter(endTime) || timeSlot.equals(endTime)));
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

    public Set<LocalTime> generateTimeSlotsInWorkingHours(LocalTime start, LocalTime end) {
        Set<LocalTime> timeSlots = new HashSet<>();
        LocalTime currentTime = start;

        while (!currentTime.isAfter(end.minusMinutes(duration))) {
            timeSlots.add(currentTime);
            currentTime = currentTime.plusMinutes(duration);
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

    public Set<Appointment> getNotReservedAppointments(Long companyId){
        return appointmentRepository.getNotReservedAppointments(companyId);
    }
}
