package medequipsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import medequipsystem.mapper.MapperUtils.DTOEntity;

import java.util.Set;

public class CustomReservationDTO implements DTOEntity {
    @JsonProperty("appointment")
    private CustomAppointmentDTO appointment;

    @JsonProperty("reservationItems")
    private Set<ReservationItemDTO> reservationItems;
    @JsonProperty("companyId")
    private Long companyId;


    public CustomReservationDTO(){}

    public CustomAppointmentDTO getAppointment() {
        return appointment;
    }

    public void setAppointment(CustomAppointmentDTO appointment) {
        this.appointment = appointment;
    }

    public Set<ReservationItemDTO> getReservationItems() {
        return reservationItems;
    }

    public void setReservationItems(Set<ReservationItemDTO> reservationItems) {
        this.reservationItems = reservationItems;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
