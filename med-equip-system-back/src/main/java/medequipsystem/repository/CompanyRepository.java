package medequipsystem.repository;

import medequipsystem.domain.Appointment;
import medequipsystem.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findFirstByName(String name);
}
