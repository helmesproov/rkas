package ee.rkas.lepinguregister.web.rest;

import ee.rkas.lepinguregister.repository.ServiceRepository;
import ee.rkas.lepinguregister.service.ServiceService;
import ee.rkas.lepinguregister.service.dto.ServiceChangeDTO;
import ee.rkas.lepinguregister.service.dto.ServiceDTO;
import ee.rkas.lepinguregister.web.rest.errors.BadRequestAlertException;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

/**
 * REST controller for managing {@link ee.rkas.lepinguregister.domain.Service}.
 */
@RestController
@RequestMapping("/api")
public class ServiceResource {
    private final Logger log = LoggerFactory.getLogger(ServiceResource.class);
    private static final String ENTITY_NAME = "service";

    @Value("clientApp")
    private String applicationName;
    private final ServiceService serviceService;

    public ServiceResource(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    /**
     * {@code GET  /services/:id} : get the "id" service.
     *
     * @param realEstateId the id of the realEstate of the service to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceDTO, or with status {@code 404 (Not Found)}.
     */
    @Operation(summary = "Get all services by real-estate id", description = "Retrieve all services that are connected to real-estate.")
    @GetMapping("/services")
    public ResponseEntity<List<ServiceDTO>> getAllServicesByRealEstateId(@RequestParam("realEstateId") Long realEstateId) {
        log.debug("REST request to get Service by Real-estate id : {}", realEstateId);
        List<ServiceDTO> services = serviceService.findAllByRealEstateId(realEstateId);
        return ResponseEntity.ok().body(services);
    }

    /**
     * {@code PUT  /services/:id} : Updates an existing service.
     *
     * @param id         the id of the service to update.
     * @param serviceChangeDTO  to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceDTO,
     * or with status {@code 400 (Bad Request)} if the serviceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Operation(summary = "Confirm pending service changes", description = "This method updates the pending service, changes the act status to 'KOOSTAMISEL' and sends an email confirming the change.")
    @PutMapping("/services/pending/{id}")
    public ResponseEntity<ServiceDTO> updatePendingService(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody ServiceChangeDTO serviceChangeDTO
    ) {
        log.debug("REST request to update Contract : {}, {}", id, serviceChangeDTO);
        if (serviceChangeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceChangeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        ServiceDTO result = serviceService.updatePendingService(serviceChangeDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }


    /**
     * {@code GET  /services/pending} : get all the pending service changes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of services in body.
     */
    @Operation(summary = "Get all pending service changes", description = "Retrieves all the pending service changes. Selects all records from pending_service table, joined with relevant contract fields.")
    @GetMapping("/services/pending")
    public ResponseEntity<List<ServiceChangeDTO>> getAllPendingServiceChanges() {
        log.debug("REST request to get all pending service changes");
        List<ServiceChangeDTO> contractDTOS = serviceService.findAllPendingServiceChanges();
        return ResponseEntity.ok().body(contractDTOS);
    }
}
