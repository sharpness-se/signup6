package se.accelerateit.signup6.reminder;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Arrays;

@Log4j2
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