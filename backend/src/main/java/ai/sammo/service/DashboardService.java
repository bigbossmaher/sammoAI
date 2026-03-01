package ai.sammo.service;

import ai.sammo.api.dto.AlertDto;
import ai.sammo.api.dto.DashboardSummaryDto;
import ai.sammo.api.dto.ThreatDto;
import ai.sammo.repository.AlertRepository;
import ai.sammo.repository.IncidentRepository;
import ai.sammo.repository.ThreatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final ThreatRepository threatRepository;
    private final IncidentRepository incidentRepository;
    private final AlertRepository alertRepository;

    public DashboardService(ThreatRepository threatRepository,
                            IncidentRepository incidentRepository,
                            AlertRepository alertRepository) {
        this.threatRepository = threatRepository;
        this.incidentRepository = incidentRepository;
        this.alertRepository = alertRepository;
    }

    public DashboardSummaryDto getSummary() {
        long activeThreats = threatRepository.findByResolved(false).size();
        long openIncidents = incidentRepository.findAll().stream()
            .filter(i -> i.getStatus() != ai.sammo.domain.Incident.IncidentStatus.RESOLVED
                && i.getStatus() != ai.sammo.domain.Incident.IncidentStatus.CLOSED)
            .count();
        long unacknowledgedAlerts = alertRepository.findByAcknowledged(false).size();

        List<ThreatDto> recentThreats = threatRepository.findAll().stream()
            .sorted((a, b) -> (b.getDetectedAt() != null && a.getDetectedAt() != null)
                ? b.getDetectedAt().compareTo(a.getDetectedAt()) : 0)
            .limit(5)
            .map(ThreatDto::from)
            .collect(Collectors.toList());

        List<AlertDto> recentAlerts = alertRepository.findAll().stream()
            .sorted((a, b) -> (b.getCreatedAt() != null && a.getCreatedAt() != null)
                ? b.getCreatedAt().compareTo(a.getCreatedAt()) : 0)
            .limit(5)
            .map(AlertDto::from)
            .collect(Collectors.toList());

        return new DashboardSummaryDto(
            activeThreats,
            openIncidents,
            unacknowledgedAlerts,
            recentThreats,
            recentAlerts
        );
    }
}
