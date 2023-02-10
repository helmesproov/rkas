package ee.rkas.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractServiceUpdateMessage {
    private BigDecimal price;
    private Instant validFrom;
    private Instant validTo;
    private Long serviceId;
}
