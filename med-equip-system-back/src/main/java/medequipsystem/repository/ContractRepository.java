package medequipsystem.repository;

import medequipsystem.domain.Appointment;
import medequipsystem.domain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    @Query("SELECT c FROM Contract c WHERE c.company.id= ?1")
    public Set<Contract> getByCompanyId(Long id);
}
