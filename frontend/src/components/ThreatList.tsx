import type { Threat } from '../types/api'
import { Box, VStack, HStack, Text, Badge, Button } from '@chakra-ui/react'

interface ThreatListProps {
  threats: Threat[]
  onResolve: (id: number) => void
}

function severityColor(s: string): string {
  const v = (s || '').toUpperCase()
  if (v === 'CRITICAL') return 'red'
  if (v === 'HIGH') return 'orange'
  if (v === 'MEDIUM') return 'yellow'
  return 'green'
}

function formatTime(iso: string | null) {
  if (!iso) return '—'
  const d = new Date(iso)
  return d.toLocaleString(undefined, { dateStyle: 'short', timeStyle: 'short' })
}

export function ThreatList({ threats, onResolve }: ThreatListProps) {
  if (threats.length === 0) {
    return (
      <Text color="gray.500" fontSize="sm">
        No recent threats.
      </Text>
    )
  }

  return (
    <VStack align="stretch" spacing={4}>
      {threats.map((t) => (
        <Box
          key={t.id}
          bg="gray.900"
          borderWidth="1px"
          borderColor="gray.700"
          borderRadius="md"
          p={4}
        >
          <HStack spacing={2} mb={2}>
            <Text fontWeight="semibold" fontSize="sm" color={`${severityColor(t.severity)}.400`}>
              {t.type?.replace(/_/g, ' ')}
            </Text>
            <Badge colorScheme={severityColor(t.severity)} size="sm" fontFamily="mono">
              {t.severity}
            </Badge>
          </HStack>
          <Text fontSize="sm" color="gray.400" mb={2} lineHeight="tall">
            {t.description}
          </Text>
          <HStack justify="space-between" fontSize="xs" color="gray.500" fontFamily="mono" mb={2}>
            <Text>{t.source} → {t.target}</Text>
            <Text>{formatTime(t.detectedAt)}</Text>
          </HStack>
          {!t.resolved && (
            <Button
              size="sm"
              colorScheme="green"
              onClick={() => onResolve(t.id)}
            >
              Mark resolved
            </Button>
          )}
        </Box>
      ))}
    </VStack>
  )
}
