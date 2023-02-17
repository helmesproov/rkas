package ee.rkas.lepinguregister.repository;

import ee.rkas.lepinguregister.domain.Service;
import ee.rkas.lepinguregister.service.dto.ServiceChangeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Service entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceRepository extends ServiceRepositoryWithBagRelationships, JpaRepository<Service, Long> {
    default List<Service> findAllByRealEstateId(Long id) {
        return this.byRealEstateId(id);
    }

    List<ServiceChangeDTO> fetchServiceChanges();
}
