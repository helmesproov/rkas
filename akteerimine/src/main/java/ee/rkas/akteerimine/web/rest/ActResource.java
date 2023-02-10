package ee.rkas.akteerimine.web.rest;

import ee.rkas.akteerimine.service.ActService;
import ee.rkas.akteerimine.service.dto.ActDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.ResponseUtil;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ActResource {
    private final Logger log = LoggerFactory.getLogger(ActResource.class);
    private final ActService actService;

    @GetMapping("/acts")
    public ResponseEntity<List<ActDTO>> getAllActs() {
        log.debug("REST request to get a page of Acts");
        List<ActDTO> page = actService.findAll();
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/acts/{id}")
    public ResponseEntity<ActDTO> getAct(@PathVariable Long id) {
        log.debug("REST request to get Act : {}", id);
        Optional<ActDTO> actDTO = actService.findOne(id);
        return ResponseUtil.wrapOrNotFound(actDTO);
    }

}
