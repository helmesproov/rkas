package ee.rkas.akteerimine.message;

import ee.rkas.akteerimine.service.dto.PendingServiceDTO;
import ee.rkas.akteerimine.service.dto.ServiceDTO;
import ee.rkas.common.ContractServiceUpdateMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final JmsTemplate jmsTemplate;

    public void updateService(SimpleMessage simpleMessage, ServiceDTO serviceDTO) {
        log.info("Sending updateServicePrice to queue: {}", simpleMessage.toString());
        jmsTemplate.convertAndSend("UpdatedServiceQueue", new ContractServiceUpdateMessage(
                serviceDTO.getPrice(),
                serviceDTO.getValidFrom(),
                serviceDTO.getValidTo(),
                serviceDTO.getId()));
    }

    @JmsListener(destination = "UpdatedServiceQueue")
    public void receiveServicePriceUpdatedMessage(ContractServiceUpdateMessage message) {
        log.info("Received {}", message);
    }
}