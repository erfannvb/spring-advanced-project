package nvb.dev.springadvancedproject.repository;

import nvb.dev.springadvancedproject.model.AlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends JpaRepository<AlertEntity, String> {
}
