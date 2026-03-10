package ai.sammo.api.dto;

/**
 * Response from ML service POST /predict/anomaly.
 */
public record MlAnomalyResponse(
    double score,
    boolean is_anomaly
) {}
