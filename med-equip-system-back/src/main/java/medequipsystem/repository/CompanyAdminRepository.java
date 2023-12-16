package medequipsystem.repository;

import medequipsystem.domain.Appointment;
import medequipsystem.domain.CompanyAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CompanyAdminRepository extends JpaRepository<CompanyAdmin, Long> {

    CompanyAdmin findByUserId(Long userId);

}
