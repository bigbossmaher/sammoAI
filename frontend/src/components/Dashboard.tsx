import type { DashboardSummary } from '../types/api'
import { api } from '../api/client'
import { StatCard } from './StatCard'
import { ThreatList } from './ThreatList'
import { AlertList } from './AlertList'
import {
  Box,
  Flex,
  Heading,
  Button,
  SimpleGrid,
  Grid,
  GridItem,
} from '@chakra-ui/react'

interface DashboardProps {
  summary: DashboardSummary
  onRefresh: () => void
}

export function Dashboard({ summary, onRefresh }: DashboardProps) {
  const handleResolveThreat = async (id: number) => {
    await api.resolveThreat(id)
    onRefresh()
  }

  const handleAcknowledgeAlert = async (id: number) => {
    await api.acknowledgeAlert(id)
    onRefresh()
  }

  return (
    <Box as="main" flex={1} p={6} maxW="1400px" marginX="auto" w="100%">
      <Flex align="center" justify="space-between" mb={6}>
        <Heading size="lg" fontWeight="600">
          Security Overview
        </Heading>
        <Button size="sm" variant="outline" colorScheme="gray" onClick={onRefresh}>
          Refresh
        </Button>
      </Flex>

      <SimpleGrid columns={{ base: 1, sm: 3 }} spacing={4} mb={6}>
        <StatCard label="Active Threats" value={summary.activeThreats} severity="high" />
        <StatCard label="Open Incidents" value={summary.openIncidents} severity="medium" />
        <StatCard
          label="Unacknowledged Alerts"
          value={summary.unacknowledgedAlerts}
          severity="critical"
        />
      </SimpleGrid>

      <Grid templateColumns={{ base: '1fr', lg: '1fr 1fr' }} gap={6}>
        <GridItem>
          <Box bg="gray.800" borderWidth="1px" borderColor="gray.700" borderRadius="md" p={5}>
            <Heading as="h2" size="sm" color="gray.400" textTransform="uppercase" letterSpacing="wider" mb={4}>
              Recent Threats
            </Heading>
            <ThreatList threats={summary.recentThreats} onResolve={handleResolveThreat} />
          </Box>
        </GridItem>
        <GridItem>
          <Box bg="gray.800" borderWidth="1px" borderColor="gray.700" borderRadius="md" p={5}>
            <Heading as="h2" size="sm" color="gray.400" textTransform="uppercase" letterSpacing="wider" mb={4}>
              Recent Alerts
            </Heading>
            <AlertList alerts={summary.recentAlerts} onAcknowledge={handleAcknowledgeAlert} />
          </Box>
        </GridItem>
      </Grid>
    </Box>
  )
}
