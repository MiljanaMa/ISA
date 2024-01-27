package medequipsystem.util;

import medequipsystem.domain.Appointment;

import javax.persistence.Column;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TimeSlot {
    public LocalTime startTime;
    public LocalTime endTime;
    public static int duration = 30;

    public TimeSlot(LocalTime startTime) {
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(duration);
    }

    public static Set<TimeSlot> generateTimeSlots(LocalTime start, LocalTime end) {
        Set<TimeSlot> timeSlots = new HashSet<TimeSlot>();
        LocalTime currentTime = start;

        while (!currentTime.isAfter(end.minusMinutes(duration))) {
            timeSlots.add(new TimeSlot(currentTime));
            currentTime = currentTime.plusMinutes(duration);
        }

        return timeSlots;
    }
    public List<Long> isTimeSlotReserved(Set<Appointment> reservedAppointments) {
        List<Long> reservedAdminIds = new ArrayList<>();

        for (Appointment appointment : reservedAppointments) {
            if (isTimeSlotOverlap(appointment.getStartTime(), appointment.getEndTime())) {
                reservedAdminIds.add(appointment.getCompanyAdmin().getId());
            }
        }
        return reservedAdminIds;
    }
    private boolean isTimeSlotOverlap(LocalTime start, LocalTime end) {
        return !((startTime.plusMinutes(duration).isBefore(start) || startTime.plusMinutes(duration).equals(start))
                || (startTime.isAfter(end) || startTime.equals(end)));
    }


}
