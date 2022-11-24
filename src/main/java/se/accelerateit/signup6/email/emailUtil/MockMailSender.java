package se.accelerateit.signup6.email.emailUtil;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class MockMailSender {

  private List<String> recipients = new ArrayList<>();

  public List<String> getAndClearRecipients() {
    List<String> r = recipients;
    recipients = new ArrayList<>();
    return r;
  }
  public void send(MimeMessage mimeMessage) throws MailException {
    try {
      String recipientsInThisMessage = Arrays.toString(mimeMessage.getRecipients(Message.RecipientType.TO));
      recipients.add(recipientsInThisMessage);

      log.info("To: " + recipientsInThisMessage);
      log.info("Message: " + mimeMessage.getContent().toString());
      log.info("------------------------------------");
    } catch (IOException | MessagingException e) {
      e.printStackTrace();
    }
  }
}