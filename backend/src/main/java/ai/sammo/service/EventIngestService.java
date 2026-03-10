package ai.sammo.service;

import ai.sammo.api.dto.EventIngestRequest;
import ai.sammo.api.dto.EventIngestResponse;
import ai.sammo.domain.Alert;
import ai.sammo.domain.Incident;
import ai.sammo.domain.Threat;
import ai.sammo.ml.MlServiceClient;
import ai.sammo.repository.AlertRepository;
import ai.sammo.repository.IncidentRepository;
import ai.sammo.repository.ThreatRepository;
import ai.sammo.sse.SseEventBroadcaster;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class EventIngestService {

    private final MlServiceClient mlServiceClient;
    private final ThreatRepository threatRepository;
    private final IncidentRepository incidentRepository;
    private final AlertRepository alertRepository;
    private final SseEventBroadcaster sseBroadcaster;
    private final double anomalyThreshold;

    public EventIngestService(
            MlServiceClient mlServiceClient,
            ThreatRepository threatRepository,
            IncidentRepository incidentRepository,
            AlertRepository alertRepository,
            SseEventBroadcaster sseBroadcaster,
            @Value("${sammo.anomaly-threshold:0.3}") double anomalyThreshold) {
        this.mlServiceClient = mlServiceClient;
        this.threatRepository = threatRepository;
        this.incidentRepository = incidentRepository;
        this.alertRepository = alertRepository;
        this.sseBroadcaster = sseBroadcaster;
        this.anomalyThreshold = anomalyThreshold;
    }

    @Transactional
    public EventIngestResponse ingest(EventIngestRequest request) {
        List<Double> metrics = request.metrics();
        if (metrics == null || metrics.size() < 2) {
            return EventIngestResponse.noAnomaly();
        }

        MlServiceClient.AnomalyResult result = mlServiceClient.predict(metrics);
        if (result == null) {
            return EventIngestResponse.noAnomaly();
        }

        boolean isAnomaly = result.isAnomaly() || result.score() >= anomalyThreshold;
        if (!isAnomaly) {
            return EventIngestResponse.noAnomaly();
        }

        Threat threat = new Threat();
        threat.setType(Threat.ThreatType.OTHER);
        threat.setSeverity(severityFromScore(result.score()));
        threat.setSource(request.source());
        threat.setTarget(request.target());
        threat.setDescription("AI-detected anomaly: score " + String.format("%.3f", result.score()) + " from event ingestion.");
        threat.setResolved(false);
        threat.setDetectedAt(Instant.now());
        threat.setAnomalyScore(result.score());
        threat.setRecommendation("Review source and target; run full scan if needed.");
        threat = threatRepository.save(threat);

        Incident incident = new Incident();
        incident.setTitle("Anomaly: " + request.source() + " → " + request.target());
        incident.setDescription("Automated detection from ML pipeline. Anomaly score: " + String.format("%.3f", result.score()));
        incident.setStatus(Incident.IncidentStatus.DETECTED);
        incident.setSeverity(incidentSeverity(threat.getSeverity()));
        incident.setThreatId(threat.getId());
        incident.setCreatedAt(Instant.now());
        incident.setUpdatedAt(Instant.now());
        incident = incidentRepository.save(incident);

        Alert alert = new Alert();
        alert.setMessage("Anomaly detected from " + request.source() + " targeting " + request.target() + " (score: " + String.format("%.3f", result.score()) + ")");
        alert.setSource("EventIngest");
        alert.setLevel(alertLevel(threat.getSeverity()));
        alert.setCreatedAt(Instant.now());
        alert.setAcknowledged(false);
        alert.setThreatId(threat.getId());
        alert.setIncidentId(incident.getId());
        alert = alertRepository.save(alert);

        sseBroadcaster.broadcastUpdate();

        return EventIngestResponse.anomaly(threat.getId(), alert.getId(), result.score());
    }

    private static Threat.Severity severityFromScore(double score) {
        if (score >= 0.5) return Threat.Severity.CRITICAL;
        if (score >= 0.35) return Threat.Severity.HIGH;
        if (score >= 0.25) return Threat.Severity.MEDIUM;
        return Threat.Severity.LOW;
    }

    private static Incident.Severity incidentSeverity(Threat.Severity s) {
        return switch (s) {
            case CRITICAL -> Incident.Severity.CRITICAL;
            case HIGH -> Incident.Severity.HIGH;
            case MEDIUM -> Incident.Severity.MEDIUM;
            default -> Incident.Severity.LOW;
        };
    }

    private static Alert.AlertLevel alertLevel(Threat.Severity s) {
        return switch (s) {
            case CRITICAL -> Alert.AlertLevel.CRITICAL;
            case HIGH -> Alert.AlertLevel.HIGH;
            case MEDIUM -> Alert.AlertLevel.MEDIUM;
            default -> Alert.AlertLevel.LOW;
        };
    }
}
