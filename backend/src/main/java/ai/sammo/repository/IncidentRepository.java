package ai.sammo.repository;

import ai.sammo.domain.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IncidentRepository extends JpaRepository<Incident, Long> {

    List<Incident> findByStatus(Incident.IncidentStatus status);

    List<Incident> findBySeverity(Incident.Severity severity);
}
