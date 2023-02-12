package ee.rkas.lepinguregister.repository;

import ee.rkas.lepinguregister.domain.Contract;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ee.rkas.lepinguregister.service.dto.ContractChangeDTO;
import ee.rkas.lepinguregister.service.dto.RealEstateServicesDTO;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ContractRepositoryWithBagRelationshipsImpl implements ContractRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Contract> fetchBagRelationships(Optional<Contract> contract) {
        return contract.map(this::fetchActs);
    }

    @Override
    public Page<Contract> fetchBagRelationships(Page<Contract> contracts) {
        return new PageImpl<>(fetchBagRelationships(contracts.getContent()), contracts.getPageable(), contracts.getTotalElements());
    }

    @Override
    public List<Contract> fetchBagRelationships(List<Contract> contracts) {
        return Optional.of(contracts).map(this::fetchActs).orElse(Collections.emptyList());
    }

    Contract fetchActs(Contract result) {
        return entityManager
            .createQuery("select contract from Contract contract left join fetch contract.acts where contract is :contract", Contract.class)
            .setParameter("contract", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Contract> fetchActs(List<Contract> contracts) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, contracts.size()).forEach(index -> order.put(contracts.get(index).getId(), index));
        List<Contract> result = entityManager
            .createQuery(
                "select distinct contract from Contract contract left join fetch contract.acts where contract in :contracts",
                Contract.class
            )
            .setParameter("contracts", contracts)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    @Override
    public List<ContractChangeDTO> fetchContractChanges() {
        return entityManager
                .createQuery(
                        "SELECT new ee.rkas.lepinguregister.service.dto.ContractChangeDTO(c.id, s.id, r.id, ps.id, ps.actId, c.name, r.name, s.name, ps.price, ps.validFrom, ps.validTo) " +
                                "FROM Contract c JOIN c.realEstates r JOIN r.services s JOIN PendingService ps ON ps.serviceId = s.id GROUP BY c.id, ps.id, r.name, s.name, s.id, r.id",
                        ContractChangeDTO.class
                )
                .getResultList();
    }
}
