package ee.rkas.lepinguregister.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ContractServicePriceUpdatedMessage {
    private String errors;
}
