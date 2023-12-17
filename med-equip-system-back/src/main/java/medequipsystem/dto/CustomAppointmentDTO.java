package medequipsystem.dto;

import medequipsystem.domain.enums.AppointmentStatus;
import medequipsystem.mapper.MapperUtils.DTOEntity;

import java.time.LocalDate;
import java.time.LocalTime;
public class CustomAppointmentDTO implements DTOEntity {

    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private AppointmentStatus status;
    public CustomAppointmentDTO(){

    }
    public CustomAppointmentDTO(Long id, LocalDate date, LocalTime startTime, LocalTime endTime, AppointmentStatus status){
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
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

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
}
