import type { Alert } from '../types/api'
import { Box, VStack, HStack, Text, Badge, Button } from '@chakra-ui/react'

interface AlertListProps {
  alerts: Alert[]
  onAcknowledge: (id: number) => void
}

function levelColor(l: string): string {
  const v = (l || '').toUpperCase()
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

export function AlertList({ alerts, onAcknowledge }: AlertListProps) {
  if (alerts.length === 0) {
    return (
      <Text color="gray.500" fontSize="sm">
        No recent alerts.
      </Text>
    )
  }

  return (
    <VStack align="stretch" spacing={3}>
      {alerts.map((a) => (
        <Box
          key={a.id}
          bg="gray.900"
          borderWidth="1px"
          borderColor="gray.700"
          borderRadius="md"
          p={4}
          opacity={a.acknowledged ? 0.7 : 1}
        >
          <HStack spacing={2} mb={2}>
            <Badge colorScheme={levelColor(a.level)} fontFamily="mono" fontSize="xs">
              {a.level}
            </Badge>
            <Text fontSize="xs" color="gray.500">
              {a.source}
            </Text>
          </HStack>
          <Text fontSize="sm" mb={2} lineHeight="tall">
            {a.message}
          </Text>
          <HStack justify="space-between" align="center">
            <Text fontSize="xs" color="gray.500">
              {formatTime(a.createdAt)}
            </Text>
            {!a.acknowledged && (
              <Button
                size="sm"
                variant="outline"
                colorScheme="blue"
                onClick={() => onAcknowledge(a.id)}
              >
                Acknowledge
              </Button>
            )}
          </HStack>
        </Box>
      ))}
    </VStack>
  )
}
