package ee.rkas.lepinguregister.repository;

import ee.rkas.lepinguregister.domain.Contract;
import ee.rkas.lepinguregister.service.dto.ServiceChangeDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ContractRepositoryWithBagRelationships {
    Optional<Contract> fetchBagRelationships(Optional<Contract> contract);

    List<Contract> fetchBagRelationships(List<Contract> contracts);

    Page<Contract> fetchBagRelationships(Page<Contract> contracts);
}
