package medequipsystem.repository;

import medequipsystem.domain.Appointment;
import medequipsystem.domain.Reservation;
import medequipsystem.domain.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Set;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    //dodati da status mora biti inprogress
    @Query("SELECT r FROM Reservation r " +
            "JOIN r.reservationItems ri " +
            "JOIN ri.equipment e " +
            "JOIN e.company c " +
            "WHERE c.id = ?1 " +
            "AND r.client.id = ?2")
    Set<Reservation> findReservationByCompanyIdAndUserId(Long companyId, Long userId);

    @Query("SELECT r FROM Reservation r WHERE r.status = 0")
    Set<Reservation> getReservationsInProgress();

    @Query("SELECT r FROM Reservation r JOIN r.appointment a WHERE a.companyAdmin.id = ?1")
    Set<Reservation> findReservationsByAdminId(Long adminId);

}
