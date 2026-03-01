package ai.sammo.api.dto;

import ai.sammo.domain.Incident;
import java.time.Instant;

public record IncidentDto(
    Long id,
    String title,
    String description,
    String status,
    String severity,
    Long threatId,
    Instant createdAt,
    Instant updatedAt,
    Instant resolvedAt,
    String responseActions
) {
    public static IncidentDto from(Incident i) {
        return new IncidentDto(
            i.getId(),
            i.getTitle(),
            i.getDescription(),
            i.getStatus() != null ? i.getStatus().name() : null,
            i.getSeverity() != null ? i.getSeverity().name() : null,
            i.getThreatId(),
            i.getCreatedAt(),
            i.getUpdatedAt(),
            i.getResolvedAt(),
            i.getResponseActions()
        );
    }
}
