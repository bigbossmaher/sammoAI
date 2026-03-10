import { NavLink } from 'react-router-dom'
import { Box, Flex, Text, HStack } from '@chakra-ui/react'

const navLinks = [
  { to: '/', label: 'Dashboard' },
  { to: '/threats', label: 'Threats' },
  { to: '/incidents', label: 'Incidents' },
  { to: '/alerts', label: 'Alerts' },
]

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
          {navLinks.map(({ to, label }) => (
            <NavLink key={to} to={to} end={to === '/'}>
              {({ isActive }) => (
                <Text
                  fontSize="sm"
                  color={isActive ? 'blue.400' : 'gray.400'}
                  _hover={{ color: 'blue.400' }}
                  cursor="pointer"
                >
                  {label}
                </Text>
              )}
            </NavLink>
          ))}
        </HStack>
      </Flex>
    </Box>
  )
}
