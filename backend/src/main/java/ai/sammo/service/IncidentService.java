package ai.sammo.service;

import ai.sammo.api.dto.IncidentDto;
import ai.sammo.domain.Incident;
import ai.sammo.repository.IncidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;

    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    public List<IncidentDto> findAll(Incident.IncidentStatus status) {
        List<Incident> list = status != null
            ? incidentRepository.findByStatus(status)
            : incidentRepository.findAll();
        return list.stream().map(IncidentDto::from).collect(Collectors.toList());
    }

    public Optional<IncidentDto> findById(Long id) {
        return incidentRepository.findById(id).map(IncidentDto::from);
    }

    @Transactional
    public IncidentDto updateStatus(Long id, Incident.IncidentStatus newStatus) {
        Incident i = incidentRepository.findById(id).orElseThrow();
        i.setStatus(newStatus);
        i.setUpdatedAt(Instant.now());
        if (newStatus == Incident.IncidentStatus.RESOLVED || newStatus == Incident.IncidentStatus.CLOSED) {
            i.setResolvedAt(Instant.now());
        }
        return IncidentDto.from(incidentRepository.save(i));
    }

    @Transactional
    public IncidentDto respond(Long id, String responseActions) {
        Incident i = incidentRepository.findById(id).orElseThrow();
        i.setResponseActions(responseActions);
        i.setStatus(Incident.IncidentStatus.CONTAINED);
        i.setUpdatedAt(Instant.now());
        return IncidentDto.from(incidentRepository.save(i));
    }
}
