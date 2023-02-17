package ee.rkas.akteerimine.service;

import ee.rkas.akteerimine.service.dto.ServiceDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServiceService {

    public List<ServiceDTO> findAllByRealEstateId(Long realEstateId) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("realEstateId", String.valueOf(realEstateId));
        return restTemplate.getForObject("http://localhost:8080/api/services?realEstateId={realEstateId}", List.class, params);
    }
}
