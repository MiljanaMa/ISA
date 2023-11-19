package medequipsystem.repository;

import medequipsystem.domain.CompanyAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyAdminRepository extends JpaRepository<CompanyAdmin, Long> {
}
