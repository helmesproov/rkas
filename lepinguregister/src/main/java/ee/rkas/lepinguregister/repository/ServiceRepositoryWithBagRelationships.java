package ee.rkas.lepinguregister.repository;

import ee.rkas.lepinguregister.domain.Service;

import java.util.List;

public interface ServiceRepositoryWithBagRelationships {

    List<Service> byRealEstateId(Long realEstateId);
}
