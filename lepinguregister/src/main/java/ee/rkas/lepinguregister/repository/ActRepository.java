package ee.rkas.lepinguregister.repository;

import ee.rkas.lepinguregister.domain.Act;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Act entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActRepository extends JpaRepository<Act, Long> {}
