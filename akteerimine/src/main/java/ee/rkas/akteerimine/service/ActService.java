package ee.rkas.akteerimine.service;

import ee.rkas.akteerimine.service.dto.ActDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ActService {

    public List<ActDTO> findAll() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://localhost:8080/api/acts", List.class);
    }

    public Optional<ActDTO> findOne(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://localhost:8080/api/acts/" + id.toString(), Optional.class);
    }
}
