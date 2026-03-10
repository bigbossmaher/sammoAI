const API_BASE = '/api'

async function fetchApi<T>(path: string, options?: RequestInit): Promise<T> {
  const res = await fetch(`${API_BASE}${path}`, {
    ...options,
    headers: { 'Content-Type': 'application/json', ...options?.headers },
  })
  if (!res.ok) throw new Error(`API error: ${res.status}`)
  return res.json()
}

export const api = {
  getSummary: () => fetchApi<import('../types/api').DashboardSummary>('/dashboard/summary'),
  getThreats: (resolved?: boolean) => {
    const q = resolved !== undefined ? `?resolved=${resolved}` : ''
    return fetchApi<import('../types/api').Threat[]>(`/threats${q}`)
  },
  resolveThreat: (id: number) =>
    fetchApi<import('../types/api').Threat>(`/threats/${id}/resolve`, { method: 'POST' }),
  getIncidents: (status?: string) => {
    const q = status ? `?status=${status}` : ''
    return fetchApi<import('../types/api').Incident[]>(`/incidents${q}`)
  },
  respondToIncident: (id: number, responseActions: string) =>
    fetchApi<import('../types/api').Incident>(`/incidents/${id}/respond`, {
      method: 'POST',
      body: JSON.stringify({ responseActions }),
    }),
  updateIncidentStatus: (id: number, status: string) =>
    fetchApi<import('../types/api').Incident>(`/incidents/${id}/status`, {
      method: 'PATCH',
      body: JSON.stringify({ status }),
    }),
  getAlerts: (acknowledged?: boolean) => {
    const q = acknowledged !== undefined ? `?acknowledged=${acknowledged}` : ''
    return fetchApi<import('../types/api').Alert[]>(`/alerts${q}`)
  },
  acknowledgeAlert: (id: number) =>
    fetchApi<import('../types/api').Alert>(`/alerts/${id}/acknowledge`, { method: 'POST' }),
}
