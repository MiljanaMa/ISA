package medequipsystem.repository;

import medequipsystem.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Set;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE a.companyAdmin.id IN (SELECT ca.id FROM CompanyAdmin ca WHERE ca.company.id= ?1)")
    public Set<Appointment> getByCompanyId(Long id);

    @Query("SELECT a FROM Appointment a WHERE a.date = ?1")
    public Set<Appointment> getByDate(LocalDate date);
}
