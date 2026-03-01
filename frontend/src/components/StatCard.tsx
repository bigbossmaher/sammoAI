import { Box, Stat, StatLabel, StatNumber } from '@chakra-ui/react'

type Severity = 'critical' | 'high' | 'medium' | 'low'

const colorMap: Record<Severity, string> = {
  critical: 'red.400',
  high: 'orange.400',
  medium: 'yellow.400',
  low: 'green.400',
}

interface StatCardProps {
  label: string
  value: number
  severity: Severity
}

export function StatCard({ label, value, severity }: StatCardProps) {
  return (
    <Box bg="gray.800" borderWidth="1px" borderColor="gray.700" borderRadius="md" p={5}>
      <Stat>
        <StatNumber color={colorMap[severity]} fontSize="2xl" fontFamily="mono">
          {value}
        </StatNumber>
        <StatLabel color="gray.400" fontWeight="medium" fontSize="sm">
          {label}
        </StatLabel>
      </Stat>
    </Box>
  )
}
