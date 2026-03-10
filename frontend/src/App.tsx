import { Routes, Route, Outlet } from 'react-router-dom'
import { Box } from '@chakra-ui/react'
import { Header } from './components/Header'
import { DashboardPage } from './pages/DashboardPage'
import { ThreatsPage } from './pages/ThreatsPage'
import { IncidentsPage } from './pages/IncidentsPage'
import { AlertsPage } from './pages/AlertsPage'

function Layout() {
  return (
    <Box minH="100vh" display="flex" flexDirection="column">
      <Header />
      <Outlet />
    </Box>
  )
}

function App() {
  return (
    <Routes>
      <Route element={<Layout />}>
        <Route path="/" element={<DashboardPage />} />
        <Route path="/threats" element={<ThreatsPage />} />
        <Route path="/incidents" element={<IncidentsPage />} />
        <Route path="/alerts" element={<AlertsPage />} />
      </Route>
    </Routes>
  )
}

export default App
