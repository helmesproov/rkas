package ee.rkas.lepinguregister.service;

import ee.rkas.lepinguregister.domain.Service;
import ee.rkas.lepinguregister.repository.ServiceRepository;
import ee.rkas.lepinguregister.service.dto.ServiceDTO;
import ee.rkas.lepinguregister.service.mapper.ServiceMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    public ServiceDTO save(ServiceDTO serviceDTO) {
        Service service = serviceMapper.toEntity(serviceDTO);
        service = serviceRepository.save(service);
        return serviceMapper.toDto(service);
    }

    public ServiceDTO update(ServiceDTO serviceDTO) {
        Service service = serviceMapper.toEntity(serviceDTO);
        service = serviceRepository.save(service);
        return serviceMapper.toDto(service);
    }

    public Optional<ServiceDTO> partialUpdate(ServiceDTO serviceDTO) {
        return serviceRepository
            .findById(serviceDTO.getId())
            .map(existingService -> {
                serviceMapper.partialUpdate(existingService, serviceDTO);

                return existingService;
            })
            .map(serviceRepository::save)
            .map(serviceMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<ServiceDTO> findAll() {
        return serviceRepository.findAll().stream().map(serviceMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Transactional(readOnly = true)
    public Optional<ServiceDTO> findOne(Long id) {
        return serviceRepository.findById(id).map(serviceMapper::toDto);
    }

    public void delete(Long id) {
        serviceRepository.deleteById(id);
    }
}
