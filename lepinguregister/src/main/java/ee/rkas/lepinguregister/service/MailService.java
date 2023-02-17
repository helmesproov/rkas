package ee.rkas.lepinguregister.service;

import ee.rkas.lepinguregister.config.ApplicationProperties;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 * Service for sending emails.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final ApplicationProperties applicationProperties;
    private final JavaMailSender javaMailSender;

    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendEmail(String subject, String content, boolean isMultipart, boolean isHtml) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String toAddress = applicationProperties.getMail().getTo();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(toAddress);
            message.setFrom(applicationProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.error("Email sent to address '{}'", toAddress);
        } catch (MailException | MessagingException e) {
            log.error("Email could not be sent to address '{}'", toAddress, e);
        }
    }

    @Async
    public void sendUpdatedServicePriceMail(String contractNumber, String realEstateName, String serviceName, BigDecimal newPrice) {
        String content = String.format("Lepingu %s objekti %s teenuse %s tunnihind on muudetud. Uus hind: %s €. Akteerimisega saab jätkata.", contractNumber, realEstateName, serviceName, newPrice);
        sendEmail("Tunnihinna muudatuste kinnitus", content, false, false);
    }
}
