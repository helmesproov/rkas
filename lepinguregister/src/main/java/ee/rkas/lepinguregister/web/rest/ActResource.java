package ee.rkas.lepinguregister.web.rest;

import ee.rkas.lepinguregister.service.ActService;
import ee.rkas.lepinguregister.service.dto.ActDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ee.rkas.lepinguregister.domain.Act}.
 */
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ActResource {
    private final Logger log = LoggerFactory.getLogger(ActResource.class);
    private final ActService actService;

    /**
     * {@code GET  /acts} : get all the acts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of acts in body.
     */
    @Operation(summary = "Get all acts", description = "Retrieve all acts from database.")
    @GetMapping("/acts")
    public ResponseEntity<List<ActDTO>> getAllActs(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Acts");
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
    @Operation(summary = "Get act by id", description = "Retrieve act by id.")
    @GetMapping("/acts/{id}")
    public ResponseEntity<ActDTO> getAct(@PathVariable Long id) {
        log.debug("REST request to get Act : {}", id);
        Optional<ActDTO> actDTO = actService.findOne(id);
        return ResponseUtil.wrapOrNotFound(actDTO);
    }

}
