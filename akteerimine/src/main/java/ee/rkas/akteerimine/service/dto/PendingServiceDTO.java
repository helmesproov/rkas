package ee.rkas.akteerimine.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

public class PendingServiceDTO implements Serializable {
    private Long id;
    private BigDecimal price;
    private Instant validFrom;
    private Instant validTo;
    private Long serviceId;

    public Long getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public Long getServiceId() {
        return serviceId;
    }
}
