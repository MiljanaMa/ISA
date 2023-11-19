package medequipsystem.repository;

import medequipsystem.domain.CompanyEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyEquipmentRepository extends JpaRepository<CompanyEquipment, Long> {
}
