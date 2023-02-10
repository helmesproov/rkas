package ee.rkas.lepinguregister.repository;

import ee.rkas.lepinguregister.domain.RealEstate;
import ee.rkas.lepinguregister.service.dto.RealEstateServicesDTO;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class RealEstateRepositoryWithBagRelationshipsImpl implements RealEstateRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<RealEstate> fetchBagRelationships(Optional<RealEstate> realEstate) {
        return realEstate.map(this::fetchContracts);
    }

    @Override
    public Page<RealEstate> fetchBagRelationships(Page<RealEstate> realEstates) {
        return new PageImpl<>(fetchBagRelationships(realEstates.getContent()), realEstates.getPageable(), realEstates.getTotalElements());
    }

    @Override
    public List<RealEstateServicesDTO> fetchRealEstatesByActId(Long actId) {
        return entityManager
                .createQuery(
                        "select new ee.rkas.lepinguregister.service.dto.RealEstateServicesDTO(r.id, r.name, count (s)) " +
                                "from RealEstate r join r.contracts c join c.acts a left join r.services s " +
                                "where a.id = :actId group by r.id, r.name",
                        RealEstateServicesDTO.class
                )
                .setParameter("actId", actId)
                .getResultList();
    }

    @Override
    public List<RealEstate> fetchBagRelationships(List<RealEstate> realEstates) {
        return Optional.of(realEstates).map(this::fetchContracts).orElse(Collections.emptyList());
    }

    RealEstate fetchContracts(RealEstate result) {
        return entityManager
                .createQuery(
                        "select realEstate from RealEstate realEstate left join fetch realEstate.contracts where realEstate is :realEstate",
                        RealEstate.class
                )
                .setParameter("realEstate", result)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getSingleResult();
    }

    List<RealEstate> fetchContracts(List<RealEstate> realEstates) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, realEstates.size()).forEach(index -> order.put(realEstates.get(index).getId(), index));
        List<RealEstate> result = entityManager
                .createQuery(
                        "select distinct realEstate from RealEstate realEstate left join fetch realEstate.contracts where realEstate in :realEstates",
                        RealEstate.class
                )
                .setParameter("realEstates", realEstates)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
