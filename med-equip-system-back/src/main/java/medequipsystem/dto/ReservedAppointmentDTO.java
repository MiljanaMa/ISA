package medequipsystem.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservedAppointmentDTO {
    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String clientFirstName;
    private String clientLastName;
    private String adminFirstName;
    private String adminLastName;

    public ReservedAppointmentDTO(){

    }

    public ReservedAppointmentDTO(LocalDate date, LocalTime startTime, LocalTime endTime,
                                  String clientFirstName, String clientLastName,
                                  String adminFirstName, String adminLastName) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.clientFirstName = clientFirstName;
        this.clientLastName = clientLastName;
        this.adminFirstName = adminFirstName;
        this.adminLastName = adminLastName;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public String getAdminFirstName() {
        return adminFirstName;
    }

    public void setAdminFirstName(String adminFirstName) {
        this.adminFirstName = adminFirstName;
    }

    public String getAdminLastName() {
        return adminLastName;
    }

    public void setAdminLastName(String adminLastName) {
        this.adminLastName = adminLastName;
    }
}
