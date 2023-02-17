package ee.rkas.lepinguregister.repository;

import ee.rkas.lepinguregister.domain.Service;
import ee.rkas.lepinguregister.service.dto.ServiceChangeDTO;

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

    @Override
    public List<ServiceChangeDTO> fetchServiceChanges() {
        return entityManager
                .createQuery(
                        " SELECT new ee.rkas.lepinguregister.service.dto.ServiceChangeDTO(c.id, s.id, r.id, ps.id, ps.actId, c.number," +
                                " r.name, s.name, s.price, s.validFrom, s.validTo, ps.price, ps.validFrom, ps.validTo) " +
                                " FROM Contract c JOIN c.realEstates r JOIN r.services s JOIN PendingService ps ON ps.serviceId = s.id" +
                                " GROUP BY c.id, ps.id, r.name, s.name, s.id, r.id",
                        ServiceChangeDTO.class
                )
                .getResultList();
    }
}
