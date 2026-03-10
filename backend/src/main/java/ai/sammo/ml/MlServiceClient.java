package ai.sammo.ml;

import java.util.List;

/**
 * Client for the Sammo-AI ML service (Python FastAPI).
 */
public interface MlServiceClient {

    /**
     * Score a feature vector for anomaly. Returns null if the ML service is unavailable.
     */
    AnomalyResult predict(List<Double> features);

    record AnomalyResult(double score, boolean isAnomaly) {}
}
