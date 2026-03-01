package ai.sammo.api;

import ai.sammo.api.dto.ThreatDto;
import ai.sammo.service.ThreatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/threats")
public class ThreatController {

    private final ThreatService threatService;

    public ThreatController(ThreatService threatService) {
        this.threatService = threatService;
    }

    @GetMapping
    public List<ThreatDto> list(@RequestParam(required = false) Boolean resolved) {
        return threatService.findAll(resolved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThreatDto> get(@PathVariable Long id) {
        return threatService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/resolve")
    public ResponseEntity<ThreatDto> resolve(@PathVariable Long id) {
        return ResponseEntity.ok(threatService.resolve(id));
    }
}
