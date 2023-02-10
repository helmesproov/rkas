package ee.rkas.lepinguregister.repository;

import ee.rkas.lepinguregister.domain.Contract;
import ee.rkas.lepinguregister.domain.RealEstate;
import java.util.List;
import java.util.Optional;

import ee.rkas.lepinguregister.service.dto.RealEstateServicesDTO;
import org.springframework.data.domain.Page;

public interface RealEstateRepositoryWithBagRelationships {
    Optional<RealEstate> fetchBagRelationships(Optional<RealEstate> realEstate);

    List<RealEstate> fetchBagRelationships(List<RealEstate> realEstates);

    Page<RealEstate> fetchBagRelationships(Page<RealEstate> realEstates);
    List<RealEstateServicesDTO> fetchRealEstatesByActId(Long actId);
}
