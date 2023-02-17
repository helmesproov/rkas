package ee.rkas.akteerimine.message;

import ee.rkas.akteerimine.service.dto.ServiceDTO;
import ee.rkas.common.ContractServiceUpdateMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.Message;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageService {
    private final JmsTemplate jmsTemplate;

    public String updateService(SimpleMessage simpleMessage, ServiceDTO serviceDTO) {
        log.info("Sending updateServicePrice to queue: {}", simpleMessage.toString());
        ContractServiceUpdateMessage content =  new ContractServiceUpdateMessage(
                serviceDTO.getPrice(),
                serviceDTO.getValidFrom(),
                serviceDTO.getValidTo(),
                serviceDTO.getId(),
                serviceDTO.getActId());
        jmsTemplate.convertAndSend("UpdatedServiceQueue", content, message -> {
            message.setJMSReplyTo(new ActiveMQQueue("ConfirmationQueue"));
            return message;
        });

        Message confirmationMessage = (Message) jmsTemplate.receive("ConfirmationQueue");

        try {
            if (confirmationMessage instanceof ActiveMQTextMessage) {
                ActiveMQTextMessage textMsg = (ActiveMQTextMessage) confirmationMessage;
                return textMsg.getText();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }
}