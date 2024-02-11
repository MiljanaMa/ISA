package medequipsystem.repository;

import medequipsystem.domain.SystemAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemAdminRepository extends JpaRepository<SystemAdmin, Long> {
    SystemAdmin findByUserId(Long userId);
}
