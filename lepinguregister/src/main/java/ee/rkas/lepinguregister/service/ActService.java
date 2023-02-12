package ee.rkas.lepinguregister.service;

import ee.rkas.lepinguregister.domain.Act;
import ee.rkas.lepinguregister.repository.ActRepository;
import ee.rkas.lepinguregister.repository.PendingServiceRepository;
import ee.rkas.lepinguregister.service.dto.ActDTO;
import ee.rkas.lepinguregister.service.mapper.ActMapper;

import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Act}.
 */
@Service
@Transactional
public class ActService {

    private final Logger log = LoggerFactory.getLogger(ActService.class);

    private final ActRepository actRepository;

    private final ActMapper actMapper;
    private final PendingServiceRepository pendingServiceRepository;

    public ActService(ActRepository actRepository, ActMapper actMapper,
                      PendingServiceRepository pendingServiceRepository) {
        this.actRepository = actRepository;
        this.actMapper = actMapper;
        this.pendingServiceRepository = pendingServiceRepository;
    }

    /**
     * Save a act.
     *
     * @param actDTO the entity to save.
     * @return the persisted entity.
     */
    public ActDTO save(ActDTO actDTO) {
        log.debug("Request to save Act : {}", actDTO);
        Act act = actMapper.toEntity(actDTO);
        act = actRepository.save(act);
        return actMapper.toDto(act);
    }

    /**
     * Update a act.
     *
     * @param actDTO the entity to save.
     * @return the persisted entity.
     */
    public ActDTO update(ActDTO actDTO) {
        log.debug("Request to update Act : {}", actDTO);
        Act act = actMapper.toEntity(actDTO);
        act = actRepository.save(act);
        return actMapper.toDto(act);
    }

    /**
     * Partially update a act.
     *
     * @param actDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ActDTO> partialUpdate(ActDTO actDTO) {
        log.debug("Request to partially update Act : {}", actDTO);

        return actRepository
                .findById(actDTO.getId())
                .map(existingAct -> {
                    actMapper.partialUpdate(existingAct, actDTO);

                    return existingAct;
                })
                .map(actRepository::save)
                .map(actMapper::toDto);
    }

    /**
     * Get all the acts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ActDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Acts");
        return actRepository.findAll(pageable).map(act -> {
            ActDTO actDTO = actMapper.toDto(act);
            actDTO.setEditPending(pendingServiceRepository.existsByActId(actDTO.getId()));
            return actDTO;
        });
    }

    /**
     * Get one act by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ActDTO> findOne(Long id) {
        log.debug("Request to get Act : {}", id);
        return actRepository.findById(id).map(act -> {
            ActDTO actDTO = actMapper.toDto(act);
            actDTO.setEditPending(pendingServiceRepository.existsByActId(actDTO.getId()));
            return actDTO;
        });
    }

    /**
     * Delete the act by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Act : {}", id);
        actRepository.deleteById(id);
    }
}
