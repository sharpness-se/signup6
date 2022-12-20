package se.accelerateit.signup6.email;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import se.accelerateit.signup6.email.emailUtil.EmailMessageBuilder;
import se.accelerateit.signup6.email.emailUtil.MockMailSender;
import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.model.User;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Service
@Log4j2
public class EmailSenderService {
    private final JavaMailSender mailSender;
    private final EmailMessageBuilder messageBuilder;

    private final MockMailSender mockMailSender;

    @Value("${signup.activate.mock.email}")
    private boolean mockEmailActivated;

    @Autowired
    public EmailSenderService(JavaMailSender mailSender, EmailMessageBuilder messageBuilder, MockMailSender mockMailSender) {
        this.mailSender = mailSender;
        this.messageBuilder = messageBuilder;
        this.mockMailSender = mockMailSender;
    }

    Properties properties=new Properties();
    Session session=Session.getInstance(properties,null);

    public void sendReminders(List<User> users, Event event) throws MessagingException{
        MimeMessage message = new MimeMessage(session);
        message.setFrom(event.getGroup().getMailFrom());
        message.setSubject("Påminnelse " + event.getName());

        for (User user : users) {
            log.info("Sending reminder to " + user.getEmail());
            message.setRecipients(Message.RecipientType.TO, user.getEmail());
            message.setText(messageBuilder.reminderMail(user, event), "UTF-8", "html");
            if (!mockEmailActivated) {
                mailSender.send(message);
            } else {
                mockMailSender.send(message);
            }


        }
    }
}
