package medequipsystem.repository;

import medequipsystem.domain.Appointment;
import medequipsystem.domain.CompanyEquipment;
import medequipsystem.dto.ReservedAppointmentDTO;
import medequipsystem.domain.enums.AppointmentStatus;
import medequipsystem.service.AppointmentService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.time.LocalDate;
import java.util.Set;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE a.companyAdmin.id IN (SELECT ca.id FROM CompanyAdmin ca WHERE ca.company.id= ?1)")
    public Set<Appointment> getByCompanyId(Long id);
    @Query("SELECT a FROM Appointment a WHERE a.id = ?1 AND a.status =?2")
    public Appointment getAvailableById(Long id, AppointmentStatus status);
    @Query("SELECT a FROM Appointment a WHERE a.companyAdmin.id IN (SELECT ca.id FROM CompanyAdmin ca WHERE ca.company.id= ?1)"
            + "AND a.date = ?2")
    public Set<Appointment> getByCompanyIdAndDate(Long id, LocalDate date, AppointmentStatus status);



    @Query("SELECT a FROM Appointment a WHERE a.date = ?1")
    public Set<Appointment> getByDate(LocalDate date);


    @Query("SELECT NEW medequipsystem.dto.ReservedAppointmentDTO(" +
            "a.date, a.startTime, a.endTime, cu.firstName, cu.lastName, cau.firstName, cau.lastName) " +
            "FROM Reservation r " +
            "JOIN Appointment a ON r.appointment.id = a.id " +
            "JOIN Client c ON r.client.id = c.id " +
            "JOIN CompanyAdmin ca ON a.companyAdmin.id = ca.id " +
            "JOIN User cu ON c.user.id = cu.id " +
            "JOIN User cau ON ca.user.id = cau.id " +
            "WHERE ca.company.id = ?1")
    public Set<ReservedAppointmentDTO> getReservedAppointmentsByCompanyId(Long companyId);

    @Query("SELECT a FROM Appointment a LEFT JOIN Reservation r ON a.id = r.appointment.id WHERE r.id IS NULL AND a.companyAdmin.id IN (SELECT ca.id FROM CompanyAdmin ca WHERE ca.company.id = ?1)")
    public Set<Appointment> getNotReservedAppointments(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    public Appointment save(Appointment appointment);
}
