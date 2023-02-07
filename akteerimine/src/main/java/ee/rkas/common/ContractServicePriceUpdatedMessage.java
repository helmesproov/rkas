package ee.rkas.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractServicePriceUpdatedMessage {
    private String errors;
}
