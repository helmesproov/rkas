package ee.rkas.akteerimine.web.rest;

import ee.rkas.akteerimine.message.MessageService;
import ee.rkas.akteerimine.service.ServiceService;
import ee.rkas.akteerimine.service.dto.ServiceDTO;
import ee.rkas.akteerimine.web.rest.errors.BadRequestAlertException;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.message.SimpleMessage;
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

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ServiceResource {
    private final Logger log = LoggerFactory.getLogger(ActResource.class);
    private final ServiceService serviceService;
    private static final String ENTITY_NAME = "service";
    private final MessageService messageService;

    @GetMapping("/services/real-estate/{id}")
    public List<ServiceDTO> getAllServicesByRealEstateId(@PathVariable Long id) {
        log.debug("REST request to get Service by Real-estate id : {}", id);
        return serviceService.findAllByRealEstateId(id);
    }

    @PutMapping("/services/{id}")
    public ResponseEntity<ServiceDTO> updateService(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody ServiceDTO serviceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Service");
        if (serviceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        messageService.updateService(new SimpleMessage(serviceDTO.getId().toString()), serviceDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert("clientApp", true, ENTITY_NAME, serviceDTO.getId().toString()))
                .body(serviceDTO);
    }
}
