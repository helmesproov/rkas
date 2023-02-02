package ee.rkas.akteerimine.message;
import ee.rkas.akteerimine.message.dto.ContractServicePriceUpdatedMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageService {
  
  private final JmsTemplate jmsTemplate;

  public void updateServicePrice(SimpleMessage simpleMessage){
    log.info("Sending updateServicePrice to queue: {}", simpleMessage.toString());
    //jmsTemplate.convertAndSend("TestQueue", new ContractServicePriceUpdateMessage(1L, 1L, BigDecimal.TEN));
    jmsTemplate.convertAndSend("TestQueue", new ContractServicePriceUpdatedMessage("error"));
  }

  @JmsListener(destination = "TestQueue")
  public void receiveServicePriceUpdatedMessage(Message message) {
    log.info("Received {}", message);
  }
}