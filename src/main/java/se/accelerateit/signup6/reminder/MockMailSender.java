package se.accelerateit.signup6.reminder;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class MockMailSender {
  public void send(MimeMessage mimeMessage) throws MailException {
    try {
      String recipientsInThisMessage = Arrays.toString(mimeMessage.getRecipients(Message.RecipientType.TO));

      log.info("To: " + recipientsInThisMessage);
      log.info("Message: " + mimeMessage.getContent().toString());
      log.info("------------------------------------");
    } catch (IOException | MessagingException e) {
      log.error("Couldn't send/log message", e);
    }
  }
}