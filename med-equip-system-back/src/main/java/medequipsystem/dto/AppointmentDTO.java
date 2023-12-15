package medequipsystem.dto;

import medequipsystem.domain.Appointment;
import medequipsystem.domain.enums.AppointmentStatus;
import medequipsystem.mapper.Mapper.DTOEntity;

import java.time.LocalDate;
import java.time.LocalTime;
public class AppointmentDTO implements DTOEntity {

    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    private AppointmentStatus status;

    private CompanyAdminDTO companyAdmin;

    public AppointmentDTO(){

    }

    /*public AppointmentDTO(Appointment a) {
        this.id = a.getId();
        this.date = a.getDate();
        this.startTime = a.getStartTime();
        this.endTime = a.getEndTime();
        this.status = a.status;

    }*/

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

    public CompanyAdminDTO getCompanyAdmin() {
        return companyAdmin;
    }

    public void setCompanyAdmin(CompanyAdminDTO companyAdmin) {
        this.companyAdmin = companyAdmin;
    }
}
