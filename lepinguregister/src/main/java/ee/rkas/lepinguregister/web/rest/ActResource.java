package ee.rkas.lepinguregister.web.rest;

import ee.rkas.lepinguregister.message.MessageService;
import ee.rkas.lepinguregister.repository.ActRepository;
import ee.rkas.lepinguregister.service.ActService;
import ee.rkas.lepinguregister.service.dto.ActDTO;
import ee.rkas.lepinguregister.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.message.SimpleMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ee.rkas.lepinguregister.domain.Act}.
 */
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ActResource {
    private final Logger log = LoggerFactory.getLogger(ActResource.class);
    private static final String ENTITY_NAME = "act";
    private final ActService actService;
    private final ActRepository actRepository;
    private final MessageService messageService;

    /**
     * {@code POST  /acts} : Create a new act.
     *
     * @param actDTO the actDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new actDTO, or with status {@code 400 (Bad Request)} if the act has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/acts")
    public ResponseEntity<ActDTO> createAct(@Valid @RequestBody ActDTO actDTO) throws URISyntaxException {
        log.debug("REST request to save Act : {}", actDTO);
        if (actDTO.getId() != null) {
            throw new BadRequestAlertException("A new act cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActDTO result = actService.save(actDTO);
        return ResponseEntity
            .created(new URI("/api/acts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("Lepinguregister", false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /acts/:id} : Updates an existing act.
     *
     * @param id the id of the actDTO to save.
     * @param actDTO the actDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated actDTO,
     * or with status {@code 400 (Bad Request)} if the actDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the actDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/acts/{id}")
    public ResponseEntity<ActDTO> updateAct(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody ActDTO actDTO)
        throws URISyntaxException {
        log.debug("REST request to update Act : {}, {}", id, actDTO);

        if (actDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, actDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!actRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ActDTO result = actService.update(actDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert("Lepinguregister", false, ENTITY_NAME, actDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /acts/:id} : Partial updates given fields of an existing act, field will ignore if it is null
     *
     * @param id the id of the actDTO to save.
     * @param actDTO the actDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated actDTO,
     * or with status {@code 400 (Bad Request)} if the actDTO is not valid,
     * or with status {@code 404 (Not Found)} if the actDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the actDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/acts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ActDTO> partialUpdateAct(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ActDTO actDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Act partially : {}, {}", id, actDTO);
        if (actDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, actDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!actRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ActDTO> result = actService.partialUpdate(actDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert("Lepinguregister", false, ENTITY_NAME, actDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /acts} : get all the acts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of acts in body.
     */
    @GetMapping("/acts")
    public ResponseEntity<List<ActDTO>> getAllActs(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Acts");
        messageService.updateServicePrice(new SimpleMessage(""));//TODO Testing. For update price etc.
        Page<ActDTO> page = actService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /acts/:id} : get the "id" act.
     *
     * @param id the id of the actDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the actDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/acts/{id}")
    public ResponseEntity<ActDTO> getAct(@PathVariable Long id) {
        log.debug("REST request to get Act : {}", id);
        Optional<ActDTO> actDTO = actService.findOne(id);
        return ResponseUtil.wrapOrNotFound(actDTO);
    }

    /**
     * {@code DELETE  /acts/:id} : delete the "id" act.
     *
     * @param id the id of the actDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/acts/{id}")
    public ResponseEntity<Void> deleteAct(@PathVariable Long id) {
        log.debug("REST request to delete Act : {}", id);
        actService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert("Lepinguregister", false, ENTITY_NAME, id.toString()))
            .build();
    }
}
