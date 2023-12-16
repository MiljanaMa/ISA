package medequipsystem.repository;

import medequipsystem.domain.Client;
import medequipsystem.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByUserId(Long userId);
}
