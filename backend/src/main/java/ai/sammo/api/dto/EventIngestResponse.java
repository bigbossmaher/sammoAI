package ai.sammo.api.dto;

/**
 * Response after ingesting an event. When an anomaly was detected,
 * threatId and alertId are present.
 */
public record EventIngestResponse(
    boolean anomalyDetected,
    Long threatId,
    Long alertId,
    Double anomalyScore
) {
    public static EventIngestResponse noAnomaly() {
        return new EventIngestResponse(false, null, null, null);
    }

    public static EventIngestResponse anomaly(Long threatId, Long alertId, double score) {
        return new EventIngestResponse(true, threatId, alertId, score);
    }
}
