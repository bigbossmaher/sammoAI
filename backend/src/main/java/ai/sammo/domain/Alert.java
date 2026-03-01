package ai.sammo.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private String source;

    @Enumerated(EnumType.STRING)
    private AlertLevel level;

    @Column(name = "created_at")
    private Instant createdAt;

    private boolean acknowledged;

    @Column(name = "threat_id")
    private Long threatId;

    @Column(name = "incident_id")
    private Long incidentId;

    public enum AlertLevel {
        CRITICAL, HIGH, MEDIUM, LOW, INFO
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public AlertLevel getLevel() { return level; }
    public void setLevel(AlertLevel level) { this.level = level; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public boolean isAcknowledged() { return acknowledged; }
    public void setAcknowledged(boolean acknowledged) { this.acknowledged = acknowledged; }

    public Long getThreatId() { return threatId; }
    public void setThreatId(Long threatId) { this.threatId = threatId; }

    public Long getIncidentId() { return incidentId; }
    public void setIncidentId(Long incidentId) { this.incidentId = incidentId; }
}
