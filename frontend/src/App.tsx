import { useState, useEffect } from 'react'
import type { DashboardSummary } from './types/api'
import { api } from './api/client'
import { Dashboard } from './components/Dashboard'
import { Header } from './components/Header'
import { Box, Center, Text, Button } from '@chakra-ui/react'

function App() {
  const [summary, setSummary] = useState<DashboardSummary | null>(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  const loadSummary = () => {
    setLoading(true)
    setError(null)
    api.getSummary()
      .then(setSummary)
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false))
  }

  useEffect(() => {
    loadSummary()
    const interval = setInterval(loadSummary, 30000)
    return () => clearInterval(interval)
  }, [])

  if (error) {
    return (
      <Box minH="100vh" display="flex" flexDirection="column">
        <Header />
        <Center flex={1} flexDirection="column" gap={4}>
          <Text color="orange.400">
            Unable to load dashboard. Is the backend running on port 8080?
          </Text>
          <Button colorScheme="gray" variant="outline" onClick={loadSummary}>
            Retry
          </Button>
        </Center>
      </Box>
    )
  }

  return (
    <Box minH="100vh" display="flex" flexDirection="column">
      <Header />
      {loading && !summary ? (
        <Center flex={1} color="gray.500">
          Loading security overview…
        </Center>
      ) : (
        summary && (
          <Dashboard summary={summary} onRefresh={loadSummary} />
        )
      )}
    </Box>
  )
}

export default App
