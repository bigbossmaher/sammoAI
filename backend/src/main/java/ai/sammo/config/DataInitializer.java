package ai.sammo.config;

import ai.sammo.domain.Alert;
import ai.sammo.domain.Incident;
import ai.sammo.domain.Threat;
import ai.sammo.repository.AlertRepository;
import ai.sammo.repository.IncidentRepository;
import ai.sammo.repository.ThreatRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(ThreatRepository threatRepo, IncidentRepository incidentRepo, AlertRepository alertRepo) {
        return args -> {
            if (threatRepo.count() > 0) return;

            Threat t1 = new Threat();
            t1.setType(Threat.ThreatType.PHISHING);
            t1.setSeverity(Threat.Severity.HIGH);
            t1.setSource("192.168.1.105");
            t1.setTarget("legislative-mail-gateway");
            t1.setDescription("Suspicious phishing attempt targeting staff credentials.");
            t1.setResolved(false);
            t1.setDetectedAt(Instant.now().minusSeconds(3600));
            t1.setRecommendation("Block source IP; run credential audit.");
            threatRepo.save(t1);

            Threat t2 = new Threat();
            t2.setType(Threat.ThreatType.UNAUTHORIZED_ACCESS);
            t2.setSeverity(Threat.Severity.CRITICAL);
            t2.setSource("10.0.2.44");
            t2.setTarget("bill-management-api");
            t2.setDescription("Multiple failed login attempts followed by successful access from unknown device.");
            t2.setResolved(false);
            t2.setDetectedAt(Instant.now().minusSeconds(7200));
            t2.setRecommendation("Revoke session; enforce MFA; review access logs.");
            threatRepo.save(t2);

            Threat t3 = new Threat();
            t3.setType(Threat.ThreatType.MALWARE);
            t3.setSeverity(Threat.Severity.MEDIUM);
            t3.setSource("external");
            t3.setTarget("file-upload-service");
            t3.setDescription("Potential malware signature in uploaded document.");
            t3.setResolved(true);
            t3.setDetectedAt(Instant.now().minusSeconds(86400));
            t3.setResolvedAt(Instant.now().minusSeconds(86000));
            t3.setRecommendation("Quarantine file; scan all recent uploads.");
            threatRepo.save(t3);

            Incident i1 = new Incident();
            i1.setTitle("Phishing campaign against legislative staff");
            i1.setDescription("Coordinated phishing emails detected; AI system flagged 12 suspicious messages.");
            i1.setStatus(Incident.IncidentStatus.INVESTIGATING);
            i1.setSeverity(Incident.Severity.HIGH);
            i1.setThreatId(t1.getId());
            i1.setCreatedAt(Instant.now().minusSeconds(3600));
            i1.setUpdatedAt(Instant.now());
            incidentRepo.save(i1);

            Incident i2 = new Incident();
            i2.setTitle("Unauthorized API access - bill management");
            i2.setDescription("Possible credential compromise; automated response triggered.");
            i2.setStatus(Incident.IncidentStatus.DETECTED);
            i2.setSeverity(Incident.Severity.CRITICAL);
            i2.setThreatId(t2.getId());
            i2.setCreatedAt(Instant.now().minusSeconds(7200));
            i2.setUpdatedAt(Instant.now());
            incidentRepo.save(i2);

            Alert a1 = new Alert();
            a1.setMessage("High-severity threat: phishing activity from 192.168.1.105");
            a1.setSource("ThreatDetector");
            a1.setLevel(Alert.AlertLevel.HIGH);
            a1.setCreatedAt(Instant.now().minusSeconds(3600));
            a1.setAcknowledged(false);
            a1.setThreatId(t1.getId());
            a1.setIncidentId(i1.getId());
            alertRepo.save(a1);

            Alert a2 = new Alert();
            a2.setMessage("CRITICAL: Unauthorized access to bill-management-api from 10.0.2.44");
            a2.setSource("RealTimeMonitor");
            a2.setLevel(Alert.AlertLevel.CRITICAL);
            a2.setCreatedAt(Instant.now().minusSeconds(7200));
            a2.setAcknowledged(false);
            a2.setThreatId(t2.getId());
            a2.setIncidentId(i2.getId());
            alertRepo.save(a2);

            Alert a3 = new Alert();
            a3.setMessage("Anomaly: unusual data export volume from legislative DB");
            a3.setSource("PredictiveDefense");
            a3.setLevel(Alert.AlertLevel.MEDIUM);
            a3.setCreatedAt(Instant.now().minusSeconds(1800));
            a3.setAcknowledged(false);
            alertRepo.save(a3);
        };
    }
}
