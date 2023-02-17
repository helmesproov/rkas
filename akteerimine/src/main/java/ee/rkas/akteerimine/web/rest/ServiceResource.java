package ee.rkas.akteerimine.web.rest;

import ee.rkas.akteerimine.message.MessageService;
import ee.rkas.akteerimine.service.ServiceService;
import ee.rkas.akteerimine.service.dto.ServiceDTO;
import ee.rkas.akteerimine.web.rest.errors.BadRequestAlertException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.message.SimpleMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ServiceResource {
    private final Logger log = LoggerFactory.getLogger(ActResource.class);
    private final ServiceService serviceService;
    private static final String ENTITY_NAME = "service";
    private final MessageService messageService;

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

    @Operation(summary = "Update service with id", description = "Send a contract service update message using ActiveMQ. Service changes are then validated and a new record is added to the pending_services table. If the message delivery and transaction is successful a confirmation message is sent back.")
    @PutMapping("/services/{id}")
    public ResponseEntity<String> updateService(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody ServiceDTO serviceDTO
    )  {
        log.debug("REST request to update Service");
        if (serviceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!isServiceValid(serviceDTO.getValidTo(), serviceDTO.getValidFrom(), serviceDTO.getPrice())) {
            throw new BadRequestAlertException("Invalid values", ENTITY_NAME, "valuesinvalid");
        }
        String confirmationMessage = messageService.updateService(new SimpleMessage(serviceDTO.getId().toString()), serviceDTO);
        if (confirmationMessage == null) {
            throw new BadRequestAlertException("Message error", ENTITY_NAME, "messageerror");
        }
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert("clientApp", true, ENTITY_NAME, serviceDTO.getId().toString()))
                .body(confirmationMessage);
    }

    private boolean isServiceValid(Instant validTo, Instant validFrom, BigDecimal price) {
        if (validFrom == null || price == null) {
            return false;
        }
        if (validTo != null) {
            if (validFrom.compareTo(validTo) >= 0) {
                return false;
            }
        }
        return price.compareTo(BigDecimal.ZERO) >= 0;
    }
}
