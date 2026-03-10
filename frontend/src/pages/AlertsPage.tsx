import { useState, useEffect, useCallback } from 'react'
import type { Alert } from '../types/api'
import { api } from '../api/client'
import { AlertList } from '../components/AlertList'
import {
  Box,
  Heading,
  Button,
  HStack,
  Select,
  FormControl,
  FormLabel,
  VStack,
  Text,
} from '@chakra-ui/react'

type Filter = 'all' | 'unacknowledged' | 'acknowledged'

export function AlertsPage() {
  const [alerts, setAlerts] = useState<Alert[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)
  const [filter, setFilter] = useState<Filter>('all')

  const loadAlerts = useCallback(() => {
    setLoading(true)
    setError(null)
    const acknowledged = filter === 'all' ? undefined : filter === 'acknowledged'
    api.getAlerts(acknowledged)
      .then(setAlerts)
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false))
  }, [filter])

  useEffect(() => {
    loadAlerts()
  }, [loadAlerts])

  const handleAcknowledge = async (id: number) => {
    await api.acknowledgeAlert(id)
    loadAlerts()
  }

  return (
    <Box as="main" flex={1} p={6} maxW="1400px" marginX="auto" w="100%">
      <VStack align="stretch" spacing={4}>
        <Heading size="lg" fontWeight="600">
          Alerts
        </Heading>
        <HStack spacing={4} wrap="wrap">
          <FormControl width="auto">
            <FormLabel fontSize="sm" mb={1}>
              Filter
            </FormLabel>
            <Select
              size="sm"
              width="44"
              value={filter}
              onChange={(e) => setFilter(e.target.value as Filter)}
              bg="gray.800"
              borderColor="gray.600"
            >
              <option value="all">All</option>
              <option value="unacknowledged">Unacknowledged only</option>
              <option value="acknowledged">Acknowledged only</option>
            </Select>
          </FormControl>
          <Button size="sm" variant="outline" colorScheme="gray" onClick={loadAlerts} mt={6}>
            Refresh
          </Button>
        </HStack>
        {error && (
          <Text color="orange.400" fontSize="sm">
            {error}
          </Text>
        )}
        {loading ? (
          <Text color="gray.500">Loading alerts…</Text>
        ) : (
          <Box bg="gray.800" borderWidth="1px" borderColor="gray.700" borderRadius="md" p={5}>
            <AlertList alerts={alerts} onAcknowledge={handleAcknowledge} />
          </Box>
        )}
      </VStack>
    </Box>
  )
}
