package ee.rkas.lepinguregister.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Contract.
 */
@Entity
@Table(name = "contract")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(
        name = "rel_contract__act",
        joinColumns = @JoinColumn(name = "contract_id"),
        inverseJoinColumns = @JoinColumn(name = "act_id")
    )
    @JsonIgnoreProperties(value = { "contracts" }, allowSetters = true)
    private Set<Act> acts = new HashSet<>();

    @ManyToMany(mappedBy = "contracts")
    @JsonIgnoreProperties(value = { "services", "contracts" }, allowSetters = true)
    private Set<RealEstate> realEstates = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Contract id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Contract name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Act> getActs() {
        return this.acts;
    }

    public void setActs(Set<Act> acts) {
        this.acts = acts;
    }

    public Contract acts(Set<Act> acts) {
        this.setActs(acts);
        return this;
    }

    public Contract addAct(Act act) {
        this.acts.add(act);
        act.getContracts().add(this);
        return this;
    }

    public Contract removeAct(Act act) {
        this.acts.remove(act);
        act.getContracts().remove(this);
        return this;
    }

    public Set<RealEstate> getRealEstates() {
        return this.realEstates;
    }

    public void setRealEstates(Set<RealEstate> realEstates) {
        if (this.realEstates != null) {
            this.realEstates.forEach(i -> i.removeContract(this));
        }
        if (realEstates != null) {
            realEstates.forEach(i -> i.addContract(this));
        }
        this.realEstates = realEstates;
    }

    public Contract realEstates(Set<RealEstate> realEstates) {
        this.setRealEstates(realEstates);
        return this;
    }

    public Contract addRealEstate(RealEstate realEstate) {
        this.realEstates.add(realEstate);
        realEstate.getContracts().add(this);
        return this;
    }

    public Contract removeRealEstate(RealEstate realEstate) {
        this.realEstates.remove(realEstate);
        realEstate.getContracts().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contract)) {
            return false;
        }
        return id != null && id.equals(((Contract) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contract{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
