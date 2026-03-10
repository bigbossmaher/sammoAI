package ai.sammo.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "threats")
public class Threat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ThreatType type;

    @Enumerated(EnumType.STRING)
    private Severity severity;

    private String source;
    private String target;
    private String description;
    private boolean resolved;

    @Column(name = "detected_at")
    private Instant detectedAt;

    @Column(name = "resolved_at")
    private Instant resolvedAt;

    private String recommendation;

    @Column(name = "anomaly_score")
    private Double anomalyScore;

    public enum ThreatType {
        MALWARE, PHISHING, RANSOMWARE, UNAUTHORIZED_ACCESS, DATA_EXFILTRATION,
        DDOS, SQL_INJECTION, ZERO_DAY, INSIDER_THREAT, OTHER
    }

    public enum Severity {
        CRITICAL, HIGH, MEDIUM, LOW, INFO
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ThreatType getType() { return type; }
    public void setType(ThreatType type) { this.type = type; }

    public Severity getSeverity() { return severity; }
    public void setSeverity(Severity severity) { this.severity = severity; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getTarget() { return target; }
    public void setTarget(String target) { this.target = target; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isResolved() { return resolved; }
    public void setResolved(boolean resolved) { this.resolved = resolved; }

    public Instant getDetectedAt() { return detectedAt; }
    public void setDetectedAt(Instant detectedAt) { this.detectedAt = detectedAt; }

    public Instant getResolvedAt() { return resolvedAt; }
    public void setResolvedAt(Instant resolvedAt) { this.resolvedAt = resolvedAt; }

    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }

    public Double getAnomalyScore() { return anomalyScore; }
    public void setAnomalyScore(Double anomalyScore) { this.anomalyScore = anomalyScore; }
}
