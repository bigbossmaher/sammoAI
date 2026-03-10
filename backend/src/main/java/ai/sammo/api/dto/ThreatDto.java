package ai.sammo.api.dto;

import ai.sammo.domain.Threat;
import java.time.Instant;

public record ThreatDto(
    Long id,
    String type,
    String severity,
    String source,
    String target,
    String description,
    boolean resolved,
    Instant detectedAt,
    Instant resolvedAt,
    String recommendation,
    Double anomalyScore
) {
    public static ThreatDto from(Threat t) {
        return new ThreatDto(
            t.getId(),
            t.getType() != null ? t.getType().name() : null,
            t.getSeverity() != null ? t.getSeverity().name() : null,
            t.getSource(),
            t.getTarget(),
            t.getDescription(),
            t.isResolved(),
            t.getDetectedAt(),
            t.getResolvedAt(),
            t.getRecommendation(),
            t.getAnomalyScore()
        );
    }
}
