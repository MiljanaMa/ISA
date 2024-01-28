package medequipsystem.repository;

import medequipsystem.domain.Appointment;
import medequipsystem.domain.Company;
import medequipsystem.domain.Contract;
import medequipsystem.domain.enums.ContractStatus;
import medequipsystem.service.CompanyService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    @Query("SELECT c FROM Contract c WHERE c.company.id= ?1")
    public Set<Contract> getByCompanyId(Long id);
    @Query("SELECT c FROM Contract c WHERE c.date= ?1 AND c.status = ?2")
    public Set<Contract> getByDateAndStatus(Integer date, ContractStatus status);
    Optional<Contract> findFirstByHospitalAndStatus(String hospital, ContractStatus status);
}
