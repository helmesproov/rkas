package ee.rkas.lepinguregister.service.mapper;

import ee.rkas.lepinguregister.domain.RealEstate;
import ee.rkas.lepinguregister.domain.Service;
import ee.rkas.lepinguregister.service.dto.RealEstateDTO;
import ee.rkas.lepinguregister.service.dto.ServiceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Service} and its DTO {@link ServiceDTO}.
 */
@Mapper(componentModel = "spring")
public interface ServiceMapper extends EntityMapper<ServiceDTO, Service> {
    @Mapping(target = "realEstate", source = "realEstate", qualifiedByName = "realEstateId")
    ServiceDTO toDto(Service s);

    @Named("realEstateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RealEstateDTO toDtoRealEstateId(RealEstate realEstate);
}
