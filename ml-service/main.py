"""
Sammo-AI ML Service — Anomaly detection and threat prediction.

This service provides:
- Anomaly scoring for behavioral/sensor data (foundation for real-time threat detection)
- Threat prediction endpoints (to be wired to backend and frontend).

Run: uvicorn main:app --reload --port 8000
"""

from contextlib import asynccontextmanager
from typing import List, Optional

import numpy as np
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
from sklearn.ensemble import IsolationForest


# In-memory model for demo; replace with persisted model and real feature pipeline in production
_model: Optional[IsolationForest] = None


def get_model() -> IsolationForest:
    global _model
    if _model is None:
        _model = IsolationForest(
            n_estimators=100,
            contamination=0.1,
            random_state=42,
        )
        # Fit on synthetic "normal" behavior (e.g. request rates, login patterns)
        X_normal = np.random.randn(500, 5) * 0.5 + 1.0
        _model.fit(X_normal)
    return _model


@asynccontextmanager
async def lifespan(app: FastAPI):
    get_model()
    yield
    # shutdown: persist model if needed


app = FastAPI(
    title="Sammo-AI ML Service",
    description="Anomaly detection and threat prediction for Sammo-AI cybersecurity platform.",
    version="1.0.0",
    lifespan=lifespan,
)

app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:5173", "http://localhost:8080"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


# --- Request/Response models ---

class AnomalyRequest(BaseModel):
    """Feature vector for one observation (e.g. request rate, failed logins, bytes in/out)."""
    features: List[float]


class AnomalyResponse(BaseModel):
    score: float  # anomaly score (higher = more anomalous)
    is_anomaly: bool  # True if score indicates potential threat


class HealthResponse(BaseModel):
    status: str
    model_loaded: bool


# --- Endpoints ---

@app.get("/health", response_model=HealthResponse)
def health():
    return HealthResponse(status="ok", model_loaded=_model is not None)


@app.post("/predict/anomaly", response_model=AnomalyResponse)
def predict_anomaly(req: AnomalyRequest):
    """Score a single observation for anomaly (potential threat)."""
    model = get_model()
    X = np.array([req.features])
    score = -model.decision_function(X)[0]  # higher = more anomalous
    is_anomaly = model.predict(X)[0] == -1
    return AnomalyResponse(score=float(score), is_anomaly=bool(is_anomaly))


@app.get("/")
def root():
    return {
        "service": "Sammo-AI ML Service",
        "docs": "/docs",
        "health": "/health",
        "predict": "POST /predict/anomaly",
    }
