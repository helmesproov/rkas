package ee.rkas.lepinguregister.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A RealEstate.
 */
@Entity
@Table(name = "real_estate")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RealEstate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "realEstate")
    @JsonIgnoreProperties(value = { "realEstate" }, allowSetters = true)
    private Set<Service> services = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_real_estate__contract",
        joinColumns = @JoinColumn(name = "real_estate_id"),
        inverseJoinColumns = @JoinColumn(name = "contract_id")
    )
    @JsonIgnoreProperties(value = { "acts", "realEstates" }, allowSetters = true)
    private Set<Contract> contracts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RealEstate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public RealEstate name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Service> getServices() {
        return this.services;
    }

    public void setServices(Set<Service> services) {
        if (this.services != null) {
            this.services.forEach(i -> i.setRealEstate(null));
        }
        if (services != null) {
            services.forEach(i -> i.setRealEstate(this));
        }
        this.services = services;
    }

    public RealEstate services(Set<Service> services) {
        this.setServices(services);
        return this;
    }

    public RealEstate addService(Service service) {
        this.services.add(service);
        service.setRealEstate(this);
        return this;
    }

    public RealEstate removeService(Service service) {
        this.services.remove(service);
        service.setRealEstate(null);
        return this;
    }

    public Set<Contract> getContracts() {
        return this.contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }

    public RealEstate contracts(Set<Contract> contracts) {
        this.setContracts(contracts);
        return this;
    }

    public RealEstate addContract(Contract contract) {
        this.contracts.add(contract);
        contract.getRealEstates().add(this);
        return this;
    }

    public RealEstate removeContract(Contract contract) {
        this.contracts.remove(contract);
        contract.getRealEstates().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RealEstate)) {
            return false;
        }
        return id != null && id.equals(((RealEstate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RealEstate{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
