package ee.rkas.lepinguregister.message;

import ee.rkas.common.ContractServiceUpdateMessage;
import ee.rkas.lepinguregister.domain.PendingService;
import ee.rkas.lepinguregister.repository.ActRepository;
import ee.rkas.lepinguregister.repository.PendingServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final PendingServiceRepository pendingServiceRepository;
    private final ActRepository actRepository;

    @JmsListener(destination = "UpdatedServiceQueue")
    public void receiveServicePriceUpdatedMessage(ContractServiceUpdateMessage message) {
        log.info("Received {}", message);
        PendingService pendingService = toPendingService(message);
        pendingServiceRepository.save(pendingService);
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