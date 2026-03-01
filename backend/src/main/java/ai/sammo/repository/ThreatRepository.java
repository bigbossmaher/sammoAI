package ai.sammo.repository;

import ai.sammo.domain.Threat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ThreatRepository extends JpaRepository<Threat, Long> {

    List<Threat> findByResolved(boolean resolved);

    List<Threat> findBySeverity(Threat.Severity severity);

    List<Threat> findByType(Threat.ThreatType type);
}
