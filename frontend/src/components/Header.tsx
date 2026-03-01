import { Box, Flex, Text, HStack } from '@chakra-ui/react'

export function Header() {
  return (
    <Box as="header" bg="gray.800" borderBottomWidth="1px" borderColor="gray.700" px={6} py={4}>
      <Flex align="center" justify="space-between">
        <Flex align="baseline" gap={3}>
          <Text fontWeight="bold" fontSize="xl" color="white">
            Sammo-AI
          </Text>
          <Text fontSize="sm" color="gray.400" fontWeight="medium">
            AI-Driven Cybersecurity
          </Text>
        </Flex>
        <HStack spacing={6}>
          <Text fontSize="sm" color="blue.400" cursor="pointer">
            Dashboard
          </Text>
          <Text fontSize="sm" color="gray.400" cursor="pointer" _hover={{ color: 'blue.400' }}>
            Threats
          </Text>
          <Text fontSize="sm" color="gray.400" cursor="pointer" _hover={{ color: 'blue.400' }}>
            Incidents
          </Text>
          <Text fontSize="sm" color="gray.400" cursor="pointer" _hover={{ color: 'blue.400' }}>
            Alerts
          </Text>
        </HStack>
      </Flex>
    </Box>
  )
}
