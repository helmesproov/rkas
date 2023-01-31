package ee.rkas.lepinguregister.service.mapper;

import ee.rkas.lepinguregister.domain.Act;
import ee.rkas.lepinguregister.service.dto.ActDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Act} and its DTO {@link ActDTO}.
 */
@Mapper(componentModel = "spring")
public interface ActMapper extends EntityMapper<ActDTO, Act> {}
