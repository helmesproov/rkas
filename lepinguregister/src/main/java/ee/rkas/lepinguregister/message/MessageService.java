package ee.rkas.lepinguregister.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ee.rkas.common.ContractServiceUpdateMessage;
import ee.rkas.lepinguregister.domain.PendingService;
import ee.rkas.lepinguregister.repository.PendingServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageService {
    private final PendingServiceRepository pendingServiceRepository;

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = "UpdatedServiceQueue")
    public void receiveServicePriceUpdatedMessage(Message message) {
        log.info("Received {}", message);
        try {
            if (message instanceof ActiveMQTextMessage) {
                ActiveMQTextMessage textMsg = (ActiveMQTextMessage) message;
                String json = textMsg.getText();
                ObjectMapper objectMapper = JsonMapper.builder()
                        .addModule(new JavaTimeModule())
                        .build();
                ContractServiceUpdateMessage contractServiceUpdateMessage = objectMapper.readValue(json, ContractServiceUpdateMessage.class);
                PendingService pendingService = toPendingService(contractServiceUpdateMessage);
                pendingServiceRepository.save(pendingService);
                jmsTemplate.convertAndSend(message.getJMSReplyTo(), "Service updated successfully");
            }
        } catch (JMSException e) {
            log.info("Error handling message:" + e.getMessage());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private PendingService toPendingService(ContractServiceUpdateMessage contractServiceUpdateMessage) {
        PendingService pendingService = new PendingService();
        pendingService.setPrice(contractServiceUpdateMessage.getPrice());
        pendingService.setServiceId(contractServiceUpdateMessage.getServiceId());
        pendingService.setValidFrom(contractServiceUpdateMessage.getValidFrom());
        pendingService.setValidTo(contractServiceUpdateMessage.getValidTo());
        pendingService.setActId(contractServiceUpdateMessage.getActId());
        return pendingService;
    }
}