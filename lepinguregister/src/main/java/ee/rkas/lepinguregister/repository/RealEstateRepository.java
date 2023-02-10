package ee.rkas.lepinguregister.repository;

import ee.rkas.lepinguregister.domain.RealEstate;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import ee.rkas.lepinguregister.domain.Service;
import ee.rkas.lepinguregister.service.dto.RealEstateServicesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RealEstate entity.
 *
 * When extending this class, extend RealEstateRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface RealEstateRepository extends RealEstateRepositoryWithBagRelationships, JpaRepository<RealEstate, Long> {
    default Optional<RealEstate> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<RealEstate> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<RealEstate> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
