# Sammo-AI Platform Architecture

This document outlines the architecture of the Sammo-AI platform as derived from the business plan.

## High-Level Components

1. **Backend (Spring Boot)** — Core API for threats, incidents, alerts, and dashboard summary. Uses H2 in-memory DB by default; swap to PostgreSQL for production.
2. **Frontend (React + Vite)** — Security operations dashboard: real-time overview, threat list, alerts, and basic actions (resolve threat, acknowledge alert).
3. **ML Service (Python/FastAPI)** — Anomaly detection and threat prediction. Currently exposes a single `POST /predict/anomaly` endpoint; can be extended with more models and integrated into the backend pipeline.

## Aligning with the Business Plan

| Business plan capability        | Implementation |
|---------------------------------|----------------|
| Real-time threat detection      | Backend stores threats; frontend displays them; ML service can score events as anomalous. |
| Predictive cyber defense        | ML service provides anomaly scoring; can feed into backend to create threats/alerts. |
| Automated incident response     | Backend supports incident status and “respond” actions; can be extended with workflows. |
| Secure data management          | Backend uses JPA; add Spring Security + auth and encryption for production. |
| Consultancy / training          | Out of scope for this repo; consider separate content or portal. |


