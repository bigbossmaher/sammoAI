package ai.sammo.service;

import ai.sammo.api.dto.ThreatDto;
import ai.sammo.domain.Threat;
import ai.sammo.repository.ThreatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ThreatService {

    private final ThreatRepository threatRepository;

    public ThreatService(ThreatRepository threatRepository) {
        this.threatRepository = threatRepository;
    }

    public List<ThreatDto> findAll(Boolean resolved) {
        List<Threat> list = resolved != null
            ? threatRepository.findByResolved(resolved)
            : threatRepository.findAll();
        return list.stream().map(ThreatDto::from).collect(Collectors.toList());
    }

    public Optional<ThreatDto> findById(Long id) {
        return threatRepository.findById(id).map(ThreatDto::from);
    }

    @Transactional
    public ThreatDto resolve(Long id) {
        Threat t = threatRepository.findById(id).orElseThrow();
        t.setResolved(true);
        t.setResolvedAt(Instant.now());
        return ThreatDto.from(threatRepository.save(t));
    }
}
