import { useState, useEffect, useCallback } from 'react'
import type { Incident } from '../types/api'
import { api } from '../api/client'
import { IncidentList } from '../components/IncidentList'
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

const STATUS_FILTER_OPTIONS = [
  { value: '', label: 'All' },
  { value: 'DETECTED', label: 'Detected' },
  { value: 'INVESTIGATING', label: 'Investigating' },
  { value: 'CONTAINED', label: 'Contained' },
  { value: 'RESOLVED', label: 'Resolved' },
  { value: 'CLOSED', label: 'Closed' },
]

export function IncidentsPage() {
  const [incidents, setIncidents] = useState<Incident[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)
  const [statusFilter, setStatusFilter] = useState<string>('')

  const loadIncidents = useCallback(() => {
    setLoading(true)
    setError(null)
    const status = statusFilter || undefined
    api.getIncidents(status)
      .then(setIncidents)
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false))
  }, [statusFilter])

  useEffect(() => {
    loadIncidents()
  }, [loadIncidents])

  const handleRespond = async (id: number, responseActions: string) => {
    await api.respondToIncident(id, responseActions)
    loadIncidents()
  }

  const handleUpdateStatus = async (id: number, status: string) => {
    await api.updateIncidentStatus(id, status)
    loadIncidents()
  }

  return (
    <Box as="main" flex={1} p={6} maxW="1400px" marginX="auto" w="100%">
      <VStack align="stretch" spacing={4}>
        <Heading size="lg" fontWeight="600">
          Incidents
        </Heading>
        <HStack spacing={4} wrap="wrap">
          <FormControl width="auto">
            <FormLabel fontSize="sm" mb={1}>
              Status
            </FormLabel>
            <Select
              size="sm"
              width="44"
              value={statusFilter}
              onChange={(e) => setStatusFilter(e.target.value)}
              bg="gray.800"
              borderColor="gray.600"
            >
              {STATUS_FILTER_OPTIONS.map((opt) => (
                <option key={opt.value || 'all'} value={opt.value}>
                  {opt.label}
                </option>
              ))}
            </Select>
          </FormControl>
          <Button size="sm" variant="outline" colorScheme="gray" onClick={loadIncidents} mt={6}>
            Refresh
          </Button>
        </HStack>
        {error && (
          <Text color="orange.400" fontSize="sm">
            {error}
          </Text>
        )}
        {loading ? (
          <Text color="gray.500">Loading incidents…</Text>
        ) : (
          <Box bg="gray.800" borderWidth="1px" borderColor="gray.700" borderRadius="md" p={5}>
            <IncidentList
              incidents={incidents}
              onRespond={handleRespond}
              onUpdateStatus={handleUpdateStatus}
            />
          </Box>
        )}
      </VStack>
    </Box>
  )
}
