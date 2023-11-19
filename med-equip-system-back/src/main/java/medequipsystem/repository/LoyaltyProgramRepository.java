package medequipsystem.repository;

import medequipsystem.domain.LoyaltyProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LoyaltyProgramRepository extends JpaRepository<LoyaltyProgram, Long> {
}
