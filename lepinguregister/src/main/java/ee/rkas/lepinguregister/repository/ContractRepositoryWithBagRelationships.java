package ee.rkas.lepinguregister.repository;

import ee.rkas.lepinguregister.domain.Contract;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ContractRepositoryWithBagRelationships {
    Optional<Contract> fetchBagRelationships(Optional<Contract> contract);

    List<Contract> fetchBagRelationships(List<Contract> contracts);

    Page<Contract> fetchBagRelationships(Page<Contract> contracts);
}
