package ee.rkas.lepinguregister.service.mapper;

import ee.rkas.lepinguregister.domain.Contract;
import ee.rkas.lepinguregister.domain.RealEstate;
import ee.rkas.lepinguregister.service.dto.ContractDTO;
import ee.rkas.lepinguregister.service.dto.RealEstateDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RealEstate} and its DTO {@link RealEstateDTO}.
 */
@Mapper(componentModel = "spring")
public interface RealEstateMapper extends EntityMapper<RealEstateDTO, RealEstate> {
    @Mapping(target = "contracts", source = "contracts", qualifiedByName = "contractIdSet")
    RealEstateDTO toDto(RealEstate s);

    @Mapping(target = "removeContract", ignore = true)
    RealEstate toEntity(RealEstateDTO realEstateDTO);

    @Named("contractId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ContractDTO toDtoContractId(Contract contract);

    @Named("contractIdSet")
    default Set<ContractDTO> toDtoContractIdSet(Set<Contract> contract) {
        return contract.stream().map(this::toDtoContractId).collect(Collectors.toSet());
    }
}
