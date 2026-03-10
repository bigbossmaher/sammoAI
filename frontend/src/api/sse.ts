/**
 * Subscribe to the backend SSE stream for live dashboard updates.
 * When the server sends an "update" event (e.g. new threat/alert), the callback is invoked.
 */
export function subscribeToLiveUpdates(onUpdate: () => void): () => void {
  const url = '/api/events/stream'
  const eventSource = new EventSource(url)

  eventSource.addEventListener('update', () => {
    onUpdate()
  })

  eventSource.onerror = () => {
    eventSource.close()
  }

  return () => eventSource.close()
}
