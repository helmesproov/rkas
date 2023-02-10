package ee.rkas.akteerimine.web.rest;

import ee.rkas.akteerimine.service.RealEstateService;
import ee.rkas.akteerimine.service.dto.RealEstateServicesDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class RealEstateResource {

    private final Logger log = LoggerFactory.getLogger(RealEstateResource.class);

    private final RealEstateService realEstateService;

    @GetMapping("/real-estates/act/{id}")
    public List<RealEstateServicesDTO> getAllRealEstatesByActId(@PathVariable Long id) {
        log.debug("REST request to get all RealEstates by Act");
        return realEstateService.findAllWithActId(id);
    }

}
