package ee.rkas.akteerimine.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link ee.rkas.lepinguregister.domain.Service} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceDTO implements Serializable {

    private Long id;

    private String name;

    private BigDecimal price;

    private Instant validFrom;

    private Instant validTo;

    private RealEstateDTO realEstate;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public RealEstateDTO getRealEstate() {
        return realEstate;
    }

    public void setRealEstate(RealEstateDTO realEstate) {
        this.realEstate = realEstate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceDTO)) {
            return false;
        }

        ServiceDTO serviceDTO = (ServiceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, serviceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", realEstate=" + getRealEstate() +
            "}";
    }
}
