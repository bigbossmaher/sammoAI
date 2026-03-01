package ai.sammo.api.dto;

import java.util.List;

public record DashboardSummaryDto(
    long activeThreats,
    long openIncidents,
    long unacknowledgedAlerts,
    List<ThreatDto> recentThreats,
    List<AlertDto> recentAlerts
) {}
