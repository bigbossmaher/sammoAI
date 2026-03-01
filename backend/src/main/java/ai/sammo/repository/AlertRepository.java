package ai.sammo.repository;

import ai.sammo.domain.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    List<Alert> findByAcknowledged(boolean acknowledged);

    List<Alert> findByLevel(Alert.AlertLevel level);
}
