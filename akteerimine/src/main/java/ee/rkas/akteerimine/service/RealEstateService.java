package ee.rkas.akteerimine.service;

import ee.rkas.akteerimine.service.dto.RealEstateServicesDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RealEstateService {
    @Value("${lepinguregister.base-url}")
    private String lepinguregisterUrl;

    public List<RealEstateServicesDTO> findAllWithActId(Long actId) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("actId", String.valueOf(actId));
        return restTemplate.getForObject(lepinguregisterUrl + "/api/real-estates?actId={actId}", List.class, params);
    }
}
