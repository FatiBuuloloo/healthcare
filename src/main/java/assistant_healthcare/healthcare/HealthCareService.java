package assistant_healthcare.healthcare;

import org.springframework.stereotype.Service;

@Service
public interface HealthCareService {
    public ExtractedFeatureDto getSummary(RequestDto request);
}
