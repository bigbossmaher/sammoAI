package ai.sammo.api.dto;

import ai.sammo.domain.Alert;
import java.time.Instant;

public record AlertDto(
    Long id,
    String message,
    String source,
    String level,
    Instant createdAt,
    boolean acknowledged,
    Long threatId,
    Long incidentId
) {
    public static AlertDto from(Alert a) {
        return new AlertDto(
            a.getId(),
            a.getMessage(),
            a.getSource(),
            a.getLevel() != null ? a.getLevel().name() : null,
            a.getCreatedAt(),
            a.isAcknowledged(),
            a.getThreatId(),
            a.getIncidentId()
        );
    }
}
