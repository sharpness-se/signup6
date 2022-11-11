package se.accelerateit.signup6.email.emailUtil;

import lombok.extern.log4j.Log4j2;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Arrays;

@Log4j2
@Service
public class MockMailSender {

  public void send(MimeMessage mimeMessage) throws MailException {
    try {
      log.info("To: " + Arrays.toString(mimeMessage.getRecipients(Message.RecipientType.TO)));
      log.info("Message: " + mimeMessage.getContent().toString());
      log.info("------------------------------------");
    } catch (IOException | MessagingException e) {
      e.printStackTrace();
    }
  }
}