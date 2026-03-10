package ai.sammo.ml;

import ai.sammo.api.dto.MlAnomalyResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class MlServiceClientImpl implements MlServiceClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public MlServiceClientImpl(
            RestTemplate restTemplate,
            @Value("${sammo.ml-service-url:http://localhost:8000}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
    }

    @Override
    public AnomalyResult predict(List<Double> features) {
        try {
            String url = baseUrl + "predict/anomaly";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, List<Double>>> request = new HttpEntity<>(Map.of("features", features), headers);
            MlAnomalyResponse resp = restTemplate.postForObject(url, request, MlAnomalyResponse.class);
            if (resp == null) return null;
            return new AnomalyResult(resp.score(), resp.is_anomaly());
        } catch (Exception e) {
            return null;
        }
    }
}
