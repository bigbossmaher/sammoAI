import { useState, useEffect, useCallback } from 'react'
import type { DashboardSummary } from '../types/api'
import { api } from '../api/client'
import { subscribeToLiveUpdates } from '../api/sse'
import { Dashboard } from '../components/Dashboard'
import { Center, Text, Button } from '@chakra-ui/react'

export function DashboardPage() {
  const [summary, setSummary] = useState<DashboardSummary | null>(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  const loadSummary = useCallback(() => {
    setLoading(true)
    setError(null)
    api.getSummary()
      .then(setSummary)
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false))
  }, [])

  const refreshSummary = useCallback(() => {
    api.getSummary().then(setSummary).catch(() => {})
  }, [])

  useEffect(() => {
    loadSummary()
    const interval = setInterval(loadSummary, 30000)
    return () => clearInterval(interval)
  }, [loadSummary])

  useEffect(() => {
    const unsubscribe = subscribeToLiveUpdates(refreshSummary)
    return unsubscribe
  }, [refreshSummary])

  if (error) {
    return (
      <Center flex={1} flexDirection="column" gap={4}>
        <Text color="orange.400">
          Unable to load dashboard. Is the backend running on port 8080?
        </Text>
        <Button colorScheme="gray" variant="outline" onClick={loadSummary}>
          Retry
        </Button>
      </Center>
    )
  }

  if (loading && !summary) {
    return (
      <Center flex={1} color="gray.500">
        Loading security overview…
      </Center>
    )
  }

  return summary ? <Dashboard summary={summary} onRefresh={loadSummary} /> : null
}
