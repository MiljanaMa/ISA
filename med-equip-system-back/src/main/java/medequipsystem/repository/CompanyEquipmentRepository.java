package medequipsystem.repository;

import medequipsystem.domain.Appointment;
import medequipsystem.domain.CompanyEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CompanyEquipmentRepository extends JpaRepository<CompanyEquipment, Long> {
}
