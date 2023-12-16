package medequipsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import medequipsystem.domain.*;
import medequipsystem.domain.enums.ReservationStatus;

import java.util.Set;

public class ReservationDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("status")
    private ReservationStatus status;
    @JsonProperty("client")
    private UserDTO client;

    @JsonProperty("appointment")
    private AppointmentDTO appointment;

    @JsonProperty("reservationItems")
    private Set<ReservationItemDTO> reservationItems;

    public ReservationDTO(){}
    /*
    public ReservationDTO(Reservation reservation){
        this(reservation.getId(), reservation.getStatus(), reservation.getClient(), reservation.getAppointment(), reservation.getReservationItems());
    }

    public ReservationDTO(Long id, ReservationStatus status, User client, Appointment appointment, Set<ReservationItemDTO> reservationItems) {
        this.id = id;
        this.status = status;
        this.client = client;
        this.appointment = appointment;
        this.reservationItems = reservationItems;
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public UserDTO getClient() {
        return client;
    }

    public void setClient(UserDTO client) {
        this.client = client;
    }

    public AppointmentDTO getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentDTO appointment) {
        this.appointment = appointment;
    }

    public Set<ReservationItemDTO> getReservationItems() {
        return reservationItems;
    }

    public void setReservationItems(Set<ReservationItemDTO> reservationItems) {
        this.reservationItems = reservationItems;
    }
}
