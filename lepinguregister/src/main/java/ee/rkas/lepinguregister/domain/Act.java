package ee.rkas.lepinguregister.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Act.
 */
@Entity
@Table(name = "act")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Act implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @ManyToMany(mappedBy = "acts")
    @JsonIgnoreProperties(value = { "acts", "realEstates" }, allowSetters = true)
    private Set<Contract> contracts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Act id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Act name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return this.status;
    }

    public Act status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Contract> getContracts() {
        return this.contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        if (this.contracts != null) {
            this.contracts.forEach(i -> i.removeAct(this));
        }
        if (contracts != null) {
            contracts.forEach(i -> i.addAct(this));
        }
        this.contracts = contracts;
    }

    public Act contracts(Set<Contract> contracts) {
        this.setContracts(contracts);
        return this;
    }

    public Act addContract(Contract contract) {
        this.contracts.add(contract);
        contract.getActs().add(this);
        return this;
    }

    public Act removeContract(Contract contract) {
        this.contracts.remove(contract);
        contract.getActs().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Act)) {
            return false;
        }
        return id != null && id.equals(((Act) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Act{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
