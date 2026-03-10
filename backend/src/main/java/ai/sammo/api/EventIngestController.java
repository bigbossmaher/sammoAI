package ai.sammo.api;

import ai.sammo.api.dto.EventIngestRequest;
import ai.sammo.api.dto.EventIngestResponse;
import ai.sammo.service.EventIngestService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class EventIngestController {

    private final EventIngestService eventIngestService;

    public EventIngestController(EventIngestService eventIngestService) {
        this.eventIngestService = eventIngestService;
    }

    @PostMapping
    public ResponseEntity<EventIngestResponse> ingest(@Valid @RequestBody EventIngestRequest request) {
        EventIngestResponse response = eventIngestService.ingest(request);
        return ResponseEntity.ok(response);
    }
}
