import { useState, useEffect, useCallback } from 'react'
import type { Threat } from '../types/api'
import { api } from '../api/client'
import { ThreatList } from '../components/ThreatList'
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

type Filter = 'all' | 'active' | 'resolved'

export function ThreatsPage() {
  const [threats, setThreats] = useState<Threat[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)
  const [filter, setFilter] = useState<Filter>('all')

  const loadThreats = useCallback(() => {
    setLoading(true)
    setError(null)
    const resolved = filter === 'all' ? undefined : filter === 'resolved'
    api.getThreats(resolved)
      .then(setThreats)
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false))
  }, [filter])

  useEffect(() => {
    loadThreats()
  }, [loadThreats])

  const handleResolve = async (id: number) => {
    await api.resolveThreat(id)
    loadThreats()
  }

  return (
    <Box as="main" flex={1} p={6} maxW="1400px" marginX="auto" w="100%">
      <VStack align="stretch" spacing={4}>
        <Heading size="lg" fontWeight="600">
          Threats
        </Heading>
        <HStack spacing={4} wrap="wrap">
          <FormControl width="auto">
            <FormLabel fontSize="sm" mb={1}>
              Filter
            </FormLabel>
            <Select
              size="sm"
              width="40"
              value={filter}
              onChange={(e) => setFilter(e.target.value as Filter)}
              bg="gray.800"
              borderColor="gray.600"
            >
              <option value="all">All</option>
              <option value="active">Active only</option>
              <option value="resolved">Resolved only</option>
            </Select>
          </FormControl>
          <Button size="sm" variant="outline" colorScheme="gray" onClick={loadThreats} mt={6}>
            Refresh
          </Button>
        </HStack>
        {error && (
          <Text color="orange.400" fontSize="sm">
            {error}
          </Text>
        )}
        {loading ? (
          <Text color="gray.500">Loading threats…</Text>
        ) : (
          <Box bg="gray.800" borderWidth="1px" borderColor="gray.700" borderRadius="md" p={5}>
            <ThreatList threats={threats} onResolve={handleResolve} />
          </Box>
        )}
      </VStack>
    </Box>
  )
}
