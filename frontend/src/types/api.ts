export interface Threat {
  id: number
  type: string
  severity: string
  source: string
  target: string
  description: string
  resolved: boolean
  detectedAt: string
  resolvedAt: string | null
  recommendation: string | null
  anomalyScore?: number | null
}

export interface Incident {
  id: number
  title: string
  description: string
  status: string
  severity: string
  threatId: number | null
  createdAt: string
  updatedAt: string
  resolvedAt: string | null
  responseActions: string | null
}

export interface Alert {
  id: number
  message: string
  source: string
  level: string
  createdAt: string
  acknowledged: boolean
  threatId: number | null
  incidentId: number | null
}

export interface DashboardSummary {
  activeThreats: number
  openIncidents: number
  unacknowledgedAlerts: number
  recentThreats: Threat[]
  recentAlerts: Alert[]
}
