package ee.rkas.lepinguregister.repository;

import ee.rkas.lepinguregister.domain.RealEstate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface RealEstateRepositoryWithBagRelationships {
    Optional<RealEstate> fetchBagRelationships(Optional<RealEstate> realEstate);

    List<RealEstate> fetchBagRelationships(List<RealEstate> realEstates);

    Page<RealEstate> fetchBagRelationships(Page<RealEstate> realEstates);
}
