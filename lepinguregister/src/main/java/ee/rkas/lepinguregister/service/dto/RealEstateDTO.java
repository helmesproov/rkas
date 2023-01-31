package ee.rkas.lepinguregister.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ee.rkas.lepinguregister.domain.RealEstate} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RealEstateDTO implements Serializable {

    private Long id;

    private String name;

    private Set<ContractDTO> contracts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ContractDTO> getContracts() {
        return contracts;
    }

    public void setContracts(Set<ContractDTO> contracts) {
        this.contracts = contracts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RealEstateDTO)) {
            return false;
        }

        RealEstateDTO realEstateDTO = (RealEstateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, realEstateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RealEstateDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", contracts=" + getContracts() +
            "}";
    }
}
