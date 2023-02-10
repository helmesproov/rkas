package ee.rkas.akteerimine.service;

import ee.rkas.akteerimine.service.dto.RealEstateServicesDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RealEstateService {
    public List<RealEstateServicesDTO> findAllWithActId(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://localhost:8080/api/real-estates/act/" + id.toString(), List.class);
    }
}
