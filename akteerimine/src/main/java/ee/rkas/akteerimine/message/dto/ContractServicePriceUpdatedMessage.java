package ee.rkas.akteerimine.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContractServicePriceUpdatedMessage {
    private String errors;
}
