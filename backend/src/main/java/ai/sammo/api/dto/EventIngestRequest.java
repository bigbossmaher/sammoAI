package ai.sammo.api.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * Request body for event ingestion. Backend runs metrics through ML service;
 * if anomaly score exceeds threshold, a Threat and Alert are created.
 */
public record EventIngestRequest(
    String source,
    String target,
    @NotNull List<Double> metrics
) {
    public EventIngestRequest {
        if (source == null) source = "unknown";
        if (target == null) target = "unknown";
    }
}
