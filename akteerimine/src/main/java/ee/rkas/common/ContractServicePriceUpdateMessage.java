package ee.rkas.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractServicePriceUpdateMessage {
    private Long contractId;
    private Long serviceId;
    private BigDecimal price;
}
