package ee.rkas.lepinguregister.service;

import ee.rkas.lepinguregister.domain.RealEstate;
import ee.rkas.lepinguregister.repository.RealEstateRepository;
import ee.rkas.lepinguregister.service.dto.RealEstateDTO;
import ee.rkas.lepinguregister.service.dto.RealEstateServicesDTO;
import ee.rkas.lepinguregister.service.mapper.RealEstateMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RealEstate}.
 */
@Service
@Transactional
public class RealEstateService {

    private final Logger log = LoggerFactory.getLogger(RealEstateService.class);

    private final RealEstateRepository realEstateRepository;

    private final RealEstateMapper realEstateMapper;

    public RealEstateService(RealEstateRepository realEstateRepository, RealEstateMapper realEstateMapper) {
        this.realEstateRepository = realEstateRepository;
        this.realEstateMapper = realEstateMapper;
    }

    /**
     * Save a realEstate.
     *
     * @param realEstateDTO the entity to save.
     * @return the persisted entity.
     */
    public RealEstateDTO save(RealEstateDTO realEstateDTO) {
        log.debug("Request to save RealEstate : {}", realEstateDTO);
        RealEstate realEstate = realEstateMapper.toEntity(realEstateDTO);
        realEstate = realEstateRepository.save(realEstate);
        return realEstateMapper.toDto(realEstate);
    }

    /**
     * Update a realEstate.
     *
     * @param realEstateDTO the entity to save.
     * @return the persisted entity.
     */
    public RealEstateDTO update(RealEstateDTO realEstateDTO) {
        log.debug("Request to update RealEstate : {}", realEstateDTO);
        RealEstate realEstate = realEstateMapper.toEntity(realEstateDTO);
        realEstate = realEstateRepository.save(realEstate);
        return realEstateMapper.toDto(realEstate);
    }

    /**
     * Partially update a realEstate.
     *
     * @param realEstateDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RealEstateDTO> partialUpdate(RealEstateDTO realEstateDTO) {
        log.debug("Request to partially update RealEstate : {}", realEstateDTO);

        return realEstateRepository
            .findById(realEstateDTO.getId())
            .map(existingRealEstate -> {
                realEstateMapper.partialUpdate(existingRealEstate, realEstateDTO);

                return existingRealEstate;
            })
            .map(realEstateRepository::save)
            .map(realEstateMapper::toDto);
    }

    /**
     * Get all the realEstates.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RealEstateDTO> findAll() {
        log.debug("Request to get all RealEstates");
        return realEstateRepository.findAll().stream().map(realEstateMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the realEstates by act id.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RealEstateServicesDTO> findAllWithActId(Long actId) {
        log.debug("Request to get all RealEstates by act id");
        return realEstateRepository.fetchRealEstatesByActId(actId);
    }

    /**
     * Get all the realEstates with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<RealEstateDTO> findAllWithEagerRelationships(Pageable pageable) {
        return realEstateRepository.findAllWithEagerRelationships(pageable).map(realEstateMapper::toDto);
    }

    /**
     * Get one realEstate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RealEstateDTO> findOne(Long id) {
        log.debug("Request to get RealEstate : {}", id);
        return realEstateRepository.findOneWithEagerRelationships(id).map(realEstateMapper::toDto);
    }

    /**
     * Delete the realEstate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RealEstate : {}", id);
        realEstateRepository.deleteById(id);
    }
}
