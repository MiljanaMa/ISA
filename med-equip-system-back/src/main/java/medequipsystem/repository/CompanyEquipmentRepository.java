package medequipsystem.repository;

import medequipsystem.domain.CompanyEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

@Repository
public interface CompanyEquipmentRepository extends JpaRepository<CompanyEquipment, Long> {

    public CompanyEquipment save(CompanyEquipment equipment);

    Optional<CompanyEquipment> findFirstByName(String name);

}
