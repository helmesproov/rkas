package ee.rkas.akteerimine.service;

import ee.rkas.akteerimine.service.dto.ActDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ActService {

    @Value("${lepinguregister.base-url}")
    private String lepinguregisterUrl;

    public List<ActDTO> findAll() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(lepinguregisterUrl + "/api/acts", List.class);
    }

    public Optional<ActDTO> findOne(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(lepinguregisterUrl + "/api/acts/" + id.toString(), Optional.class);
    }
}
