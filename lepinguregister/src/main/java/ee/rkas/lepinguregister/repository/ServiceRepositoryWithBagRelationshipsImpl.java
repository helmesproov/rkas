package ee.rkas.lepinguregister.repository;

import ee.rkas.lepinguregister.domain.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ServiceRepositoryWithBagRelationshipsImpl implements ServiceRepositoryWithBagRelationships {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Service> byRealEstateId(Long realEstateId) {
        return entityManager
                .createQuery(
                        "select s from Service s where s.realEstate.id = :realEstateId",
                        Service.class
                )
                .setParameter("realEstateId", realEstateId)
                .getResultList();
    }
}
