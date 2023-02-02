package ee.rkas.akteerimine.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ContractServicePriceUpdateMessage {
    private Long contractId;
    private Long serviceId;
    private BigDecimal price;
}
