package ee.rkas.lepinguregister.repository;

import ee.rkas.lepinguregister.domain.PendingService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PendingServiceRepository extends JpaRepository<PendingService, Long> {
    boolean existsByActId(Long actId);

    boolean existsByServiceId(Long serviceId);

    PendingService findByServiceId(Long id);

}
