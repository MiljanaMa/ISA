package medequipsystem.repository;

import medequipsystem.domain.CompanyEquipmentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyEquipmentItemRepository extends JpaRepository<CompanyEquipmentItem, Long> {
}
