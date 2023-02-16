package ee.rkas.lepinguregister.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ee.rkas.lepinguregister.domain.Contract} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContractDTO implements Serializable {

    private Long id;

    private String name;

    private String number;
    private Set<ActDTO> acts = new HashSet<>();

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

    public Set<ActDTO> getActs() {
        return acts;
    }

    public void setActs(Set<ActDTO> acts) {
        this.acts = acts;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContractDTO)) {
            return false;
        }

        ContractDTO contractDTO = (ContractDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contractDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContractDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", acts=" + getActs() +
            "}";
    }
}
