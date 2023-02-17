package ee.rkas.lepinguregister.web.rest;

import ee.rkas.lepinguregister.service.RealEstateService;
import ee.rkas.lepinguregister.service.dto.RealEstateServicesDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing {@link ee.rkas.lepinguregister.domain.RealEstate}.
 */
@RestController
@RequestMapping("/api")
public class RealEstateResource {

    private final Logger log = LoggerFactory.getLogger(RealEstateResource.class);

    private final RealEstateService realEstateService;

    public RealEstateResource(RealEstateService realEstateService) {
        this.realEstateService = realEstateService;
    }

    /**
     * {@code GET  /real-estates} : get all the realEstates with actId.
     *
     * @param actId the id of the act of the real-estate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of realEstates in body.
     */
    @Operation(summary = "Get all real-estates by act id",
            description = "Retrieve all real estates and number of connected services from the database by act id.")
    @GetMapping("/real-estates")
    public ResponseEntity<List<RealEstateServicesDTO>> getAllRealEstatesByActId(@RequestParam("actId") Long actId) {
        log.debug("REST request to get all RealEstates by Act id");
        List<RealEstateServicesDTO> realEstates = realEstateService.findAllWithActId(actId);
        return ResponseEntity.ok().body(realEstates);
    }

}
