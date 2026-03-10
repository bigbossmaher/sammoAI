# Sammo-AI

**AI-driven cybersecurity for legislative systems and critical infrastructure.**

Sammo-AI is an AI-driven cybersecurity company focused on strengthening the cybersecurity of U.S. legislative systems and critical national infrastructure. This repository contains the core platform: real-time threat detection, predictive cyber defense, automated incident response, and secure data management.

## Vision 

- **Real-time threat detection and analysis** — Monitor systems and networks for unauthorized access and suspicious activity
- **Predictive cyber defense** — ML algorithms to forecast vulnerabilities and proactively address them
- **Automated incident response** — Swift containment and response to detected threats
- **Secure data management** — Protect sensitive legislative and governmental data
- **Consultancy & training** — Assessments, audits, and cybersecurity best practices

## Target Clients

- Governmental agencies (federal, state, local)
- Critical infrastructure: finance, healthcare, energy, telecommunications
- Regulatory bodies, educational institutions, national security and defense

## Repository Structure

```
sammo-ai/
├── backend/          # Spring Boot API (Java) — threats, incidents, alerts, auth
├── frontend/         # React dashboard — monitoring, alerts, threat visualization
├── ml-service/       # Python ML service — anomaly detection, threat prediction
└── docs/             # Architecture and deployment notes
```

## Tech Stack

| Layer     | Technology |
|----------|------------|
| Backend  | Java 17+, Spring Boot, Spring Security, REST APIs |
| Frontend | React, Vite, TypeScript |
| ML/AI    | Python, scikit-learn (anomaly detection), expandable to deep learning |
| Data     | PostgreSQL (configurable), in-memory for demo |
| DevOps   | Docker, CI/CD-ready (Jenkins) |

## Quick Start

### Prerequisites

- **Backend:** JDK 17+, Maven or Gradle  
- **Frontend:** Node.js 18+, npm or pnpm  
- **ML service (optional):** Python 3.10+, pip  

### Backend

```bash
cd backend
./mvnw spring-boot:run
# API: http://localhost:8080
```

### Frontend

```bash
cd frontend
npm install
npm run dev
# Dashboard: http://localhost:5173
```

### ML Service (optional)

```bash
cd ml-service
pip install -r requirements.txt
python -m uvicorn main:app --reload
# ML API: http://localhost:8000
```

## API Overview

| Endpoint | Description |
|----------|-------------|
| `GET /api/threats` | List and filter security threats |
| `GET /api/incidents` | List and filter incidents |
| `GET /api/alerts` | Real-time security alerts |
| `GET /api/dashboard/summary` | Dashboard metrics and KPIs |
| `GET /api/events/stream` | SSE stream for live updates (new threats/alerts) |
| `POST /api/events` | Ingest event; ML scores it; creates threat+alert if anomaly |
| `POST /api/incidents/{id}/respond` | Trigger automated incident response |

### Real-time pipeline

1. Send events to `POST /api/events` with `{ "source", "target", "metrics": [5 floats] }`.
2. Backend calls the ML service (`POST /predict/anomaly`); if the score exceeds the threshold, a Threat, Incident, and Alert are created.
3. The dashboard subscribes to `GET /api/events/stream` (SSE) and refreshes when new threats/alerts appear.

## License & Contact

**Sammo-AI** — Mr. Maher Sammoudi, Founder & CEO  
