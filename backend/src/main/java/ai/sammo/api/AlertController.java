package ai.sammo.api;

import ai.sammo.api.dto.AlertDto;
import ai.sammo.service.AlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping
    public List<AlertDto> list(@RequestParam(required = false) Boolean acknowledged) {
        return alertService.findAll(acknowledged);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertDto> get(@PathVariable Long id) {
        return alertService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/acknowledge")
    public ResponseEntity<AlertDto> acknowledge(@PathVariable Long id) {
        return ResponseEntity.ok(alertService.acknowledge(id));
    }
}
