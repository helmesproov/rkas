package ee.rkas.lepinguregister.service.mapper;

import ee.rkas.lepinguregister.domain.Act;
import ee.rkas.lepinguregister.domain.Contract;
import ee.rkas.lepinguregister.service.dto.ActDTO;
import ee.rkas.lepinguregister.service.dto.ContractDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Contract} and its DTO {@link ContractDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContractMapper extends EntityMapper<ContractDTO, Contract> {
    @Mapping(target = "acts", source = "acts", qualifiedByName = "actIdSet")
    ContractDTO toDto(Contract s);

    @Mapping(target = "removeAct", ignore = true)
    Contract toEntity(ContractDTO contractDTO);

    @Named("actId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ActDTO toDtoActId(Act act);

    @Named("actIdSet")
    default Set<ActDTO> toDtoActIdSet(Set<Act> act) {
        return act.stream().map(this::toDtoActId).collect(Collectors.toSet());
    }
}
