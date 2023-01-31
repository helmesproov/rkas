package ee.rkas.lepinguregister.web.rest;

import ee.rkas.lepinguregister.repository.RealEstateRepository;
import ee.rkas.lepinguregister.service.RealEstateService;
import ee.rkas.lepinguregister.service.dto.RealEstateDTO;
import ee.rkas.lepinguregister.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ee.rkas.lepinguregister.domain.RealEstate}.
 */
@RestController
@RequestMapping("/api")
public class RealEstateResource {

    private final Logger log = LoggerFactory.getLogger(RealEstateResource.class);

    private static final String ENTITY_NAME = "realEstate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RealEstateService realEstateService;

    private final RealEstateRepository realEstateRepository;

    public RealEstateResource(RealEstateService realEstateService, RealEstateRepository realEstateRepository) {
        this.realEstateService = realEstateService;
        this.realEstateRepository = realEstateRepository;
    }

    /**
     * {@code POST  /real-estates} : Create a new realEstate.
     *
     * @param realEstateDTO the realEstateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new realEstateDTO, or with status {@code 400 (Bad Request)} if the realEstate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/real-estates")
    public ResponseEntity<RealEstateDTO> createRealEstate(@Valid @RequestBody RealEstateDTO realEstateDTO) throws URISyntaxException {
        log.debug("REST request to save RealEstate : {}", realEstateDTO);
        if (realEstateDTO.getId() != null) {
            throw new BadRequestAlertException("A new realEstate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RealEstateDTO result = realEstateService.save(realEstateDTO);
        return ResponseEntity
            .created(new URI("/api/real-estates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /real-estates/:id} : Updates an existing realEstate.
     *
     * @param id the id of the realEstateDTO to save.
     * @param realEstateDTO the realEstateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated realEstateDTO,
     * or with status {@code 400 (Bad Request)} if the realEstateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the realEstateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/real-estates/{id}")
    public ResponseEntity<RealEstateDTO> updateRealEstate(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RealEstateDTO realEstateDTO
    ) throws URISyntaxException {
        if (true) throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        log.debug("REST request to update RealEstate : {}, {}", id, realEstateDTO);
        if (realEstateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, realEstateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!realEstateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RealEstateDTO result = realEstateService.update(realEstateDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, realEstateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /real-estates/:id} : Partial updates given fields of an existing realEstate, field will ignore if it is null
     *
     * @param id the id of the realEstateDTO to save.
     * @param realEstateDTO the realEstateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated realEstateDTO,
     * or with status {@code 400 (Bad Request)} if the realEstateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the realEstateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the realEstateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/real-estates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RealEstateDTO> partialUpdateRealEstate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RealEstateDTO realEstateDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RealEstate partially : {}, {}", id, realEstateDTO);
        if (realEstateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, realEstateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!realEstateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RealEstateDTO> result = realEstateService.partialUpdate(realEstateDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, realEstateDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /real-estates} : get all the realEstates.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of realEstates in body.
     */
    @GetMapping("/real-estates")
    public List<RealEstateDTO> getAllRealEstates(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all RealEstates");
        return realEstateService.findAll();
    }

    /**
     * {@code GET  /real-estates/:id} : get the "id" realEstate.
     *
     * @param id the id of the realEstateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the realEstateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/real-estates/{id}")
    public ResponseEntity<RealEstateDTO> getRealEstate(@PathVariable Long id) {
        log.debug("REST request to get RealEstate : {}", id);
        Optional<RealEstateDTO> realEstateDTO = realEstateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(realEstateDTO);
    }

    /**
     * {@code DELETE  /real-estates/:id} : delete the "id" realEstate.
     *
     * @param id the id of the realEstateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/real-estates/{id}")
    public ResponseEntity<Void> deleteRealEstate(@PathVariable Long id) {
        log.debug("REST request to delete RealEstate : {}", id);
        realEstateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
