package medequipsystem.repository;

import medequipsystem.domain.Client;
import medequipsystem.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByUserId(Long userId);

    @Query("SELECT DISTINCT r.client FROM Reservation r WHERE r.appointment.companyAdmin.id = ?1")
    Set<Client> findClientsByAdminId(Long id);
}
