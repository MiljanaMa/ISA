package medequipsystem.domain;

import medequipsystem.domain.enums.ReservationStatus;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @SequenceGenerator(name = "mySeqGenV2", sequenceName = "mySeqV2", initialValue = 30, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV2")
    @Column(name="id", unique=true, nullable=false)
    private Long id;
    @Column(name="status", nullable = false)
    public ReservationStatus status;
    @ManyToOne()
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;
    @ManyToOne(optional = true)
    @JoinColumn(name = "appointment_id", referencedColumnName = "id")
    private Appointment appointment;

    //@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "reservation_id")
    private Set<ReservationItem> reservationItems = new HashSet<>();

    public Reservation() {}

    public Reservation(Long id, ReservationStatus status, Client client, Appointment appointment, Set<ReservationItem> reservationItems) {
        this.id = id;
        this.status = status;
        this.client = client;
        this.appointment = appointment;
        this.reservationItems = reservationItems;
    }

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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Set<ReservationItem> getReservationItems() {
        return reservationItems;
    }

    public void setReservationItems(Set<ReservationItem> reservationItems) {
        this.reservationItems = reservationItems;
    }
    public void addReservationItem(ReservationItem reservationItem) {
        reservationItems.add(reservationItem);
        //reservationItems.setReservationItems(this);
    }

    public void removeReservationItem(ReservationItem reservationItem) {
        reservationItems.remove(reservationItem);
        //reservationItem.setReservation(null);
    }
}
