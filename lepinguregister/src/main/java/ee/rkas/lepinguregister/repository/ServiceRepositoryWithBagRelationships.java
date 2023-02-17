package ee.rkas.lepinguregister.repository;

import ee.rkas.lepinguregister.domain.Service;
import ee.rkas.lepinguregister.service.dto.ServiceChangeDTO;

import java.util.List;

public interface ServiceRepositoryWithBagRelationships {

    List<Service> byRealEstateId(Long realEstateId);
    List<ServiceChangeDTO> fetchServiceChanges();
}
