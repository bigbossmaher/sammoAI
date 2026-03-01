package ai.sammo.service;

import ai.sammo.api.dto.AlertDto;
import ai.sammo.domain.Alert;
import ai.sammo.repository.AlertRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlertService {

    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public List<AlertDto> findAll(Boolean acknowledged) {
        List<Alert> list = acknowledged != null
            ? alertRepository.findByAcknowledged(acknowledged)
            : alertRepository.findAll();
        return list.stream().map(AlertDto::from).collect(Collectors.toList());
    }

    public Optional<AlertDto> findById(Long id) {
        return alertRepository.findById(id).map(AlertDto::from);
    }

    @Transactional
    public AlertDto acknowledge(Long id) {
        Alert a = alertRepository.findById(id).orElseThrow();
        a.setAcknowledged(true);
        return AlertDto.from(alertRepository.save(a));
    }
}
