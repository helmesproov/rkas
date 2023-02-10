package ee.rkas.akteerimine.service;

import ee.rkas.akteerimine.service.dto.ServiceDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ServiceService {

    public List<ServiceDTO> findAllByRealEstateId(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://localhost:8080/api/services/real-estate/" + id.toString(), List.class);
    }
}
