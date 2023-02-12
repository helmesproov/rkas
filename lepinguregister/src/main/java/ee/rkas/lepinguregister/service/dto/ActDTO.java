package ee.rkas.lepinguregister.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ee.rkas.lepinguregister.domain.Act} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ActDTO implements Serializable {

    private Long id;

    private String name;

    private boolean isEditPending;

    @NotNull
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isEditPending() {
        return isEditPending;
    }

    public void setEditPending(boolean editPending) {
        isEditPending = editPending;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActDTO)) {
            return false;
        }

        ActDTO actDTO = (ActDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, actDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
