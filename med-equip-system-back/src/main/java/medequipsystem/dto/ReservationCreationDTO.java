package medequipsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

public class ReservationCreationDTO {
        @JsonProperty("appointment")
        private AppointmentDTO appointment;

        @JsonProperty("reservationItems")
        private Set<ReservationItemDTO> reservationItems;

        public ReservationCreationDTO(){}

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
