package ee.rkas.lepinguregister.web.rest;

import ee.rkas.lepinguregister.service.ContractService;
import ee.rkas.lepinguregister.service.dto.ContractDTO;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link ee.rkas.lepinguregister.domain.Contract}.
 */
@RestController
@RequestMapping("/api")
public class ContractResource {
    private final Logger log = LoggerFactory.getLogger(ContractResource.class);
    private final ContractService contractService;

    public ContractResource(ContractService contractService) {
        this.contractService = contractService;
    }

    /**
     * {@code GET  /contracts} : get all the contracts.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contracts in body.
     */
    @Operation(summary = "Get all contracts", description = "Retrieve all contracts from database.")
    @GetMapping("/contracts")
    public ResponseEntity<List<ContractDTO>> getAllContracts(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all contracts");
        List<ContractDTO> contractDTOS = contractService.findAll();
        return ResponseEntity.ok().body(contractDTOS);
    }
}
