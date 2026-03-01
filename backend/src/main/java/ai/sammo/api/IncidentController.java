package ai.sammo.api;

import ai.sammo.api.dto.IncidentDto;
import ai.sammo.domain.Incident;
import ai.sammo.service.IncidentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @GetMapping
    public List<IncidentDto> list(@RequestParam(required = false) String status) {
        Incident.IncidentStatus s = status != null ? Incident.IncidentStatus.valueOf(status) : null;
        return incidentService.findAll(s);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidentDto> get(@PathVariable Long id) {
        return incidentService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<IncidentDto> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        Incident.IncidentStatus newStatus = Incident.IncidentStatus.valueOf(body.get("status"));
        return ResponseEntity.ok(incidentService.updateStatus(id, newStatus));
    }

    @PostMapping("/{id}/respond")
    public ResponseEntity<IncidentDto> respond(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String actions = body.getOrDefault("responseActions", "");
        return ResponseEntity.ok(incidentService.respond(id, actions));
    }
}
