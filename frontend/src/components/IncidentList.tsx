import type { Incident } from '../types/api'
import { Box, VStack, HStack, Text, Badge, Button, Textarea, Select } from '@chakra-ui/react'
import { useState } from 'react'

interface IncidentListProps {
  incidents: Incident[]
  onRespond: (id: number, responseActions: string) => void
  onUpdateStatus: (id: number, status: string) => void
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

const STATUS_OPTIONS = ['DETECTED', 'INVESTIGATING', 'CONTAINED', 'RESOLVED', 'CLOSED']

export function IncidentList({ incidents, onRespond, onUpdateStatus }: IncidentListProps) {
  const [respondingId, setRespondingId] = useState<number | null>(null)
  const [responseText, setResponseText] = useState('')

  if (incidents.length === 0) {
    return (
      <Text color="gray.500" fontSize="sm">
        No incidents.
      </Text>
    )
  }

  const handleSubmitRespond = (id: number) => {
    onRespond(id, responseText)
    setRespondingId(null)
    setResponseText('')
  }

  return (
    <VStack align="stretch" spacing={4}>
      {incidents.map((i) => (
        <Box
          key={i.id}
          bg="gray.900"
          borderWidth="1px"
          borderColor="gray.700"
          borderRadius="md"
          p={4}
        >
          <HStack spacing={2} mb={2}>
            <Text fontWeight="semibold" fontSize="sm">
              {i.title}
            </Text>
            <Badge colorScheme={severityColor(i.severity)} size="sm" fontFamily="mono">
              {i.severity}
            </Badge>
            <Badge colorScheme="gray" size="sm" fontFamily="mono">
              {i.status}
            </Badge>
          </HStack>
          <Text fontSize="sm" color="gray.400" mb={2} lineHeight="tall">
            {i.description}
          </Text>
          <HStack fontSize="xs" color="gray.500" fontFamily="mono" mb={3}>
            <Text>Created {formatTime(i.createdAt)}</Text>
            {i.responseActions && (
              <Text title={i.responseActions}>
                · {i.responseActions.length > 50 ? `${i.responseActions.slice(0, 50)}…` : i.responseActions}
              </Text>
            )}
          </HStack>
          <HStack spacing={2} flexWrap="wrap" gap={2}>
            <Select
              size="sm"
              width="36"
              bg="gray.800"
              borderColor="gray.600"
              value={i.status}
              onChange={(e: React.ChangeEvent<HTMLSelectElement>) => onUpdateStatus(i.id, e.target.value)}
            >
              {STATUS_OPTIONS.map((s) => (
                <option key={s} value={s}>
                  {s}
                </option>
              ))}
            </Select>
            {respondingId === i.id ? (
              <>
                <Textarea
                  size="sm"
                  placeholder="Response actions…"
                  value={responseText}
                  onChange={(e) => setResponseText(e.target.value)}
                  bg="gray.800"
                  borderColor="gray.600"
                  rows={2}
                  width="200px"
                />
                <Button size="sm" colorScheme="blue" onClick={() => handleSubmitRespond(i.id)}>
                  Submit
                </Button>
                <Button size="sm" variant="ghost" onClick={() => setRespondingId(null)}>
                  Cancel
                </Button>
              </>
            ) : (
              <Button
                size="sm"
                variant="outline"
                colorScheme="blue"
                onClick={() => setRespondingId(i.id)}
              >
                Respond
              </Button>
            )}
          </HStack>
        </Box>
      ))}
    </VStack>
  )
}
