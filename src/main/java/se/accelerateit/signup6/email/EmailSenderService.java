package se.accelerateit.signup6.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import se.accelerateit.signup6.email.emailUtil.EmailMessageBuilder;
import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.model.User;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Service
public class EmailSenderService {
    private final JavaMailSender mailSender;
    private final EmailMessageBuilder messageBuilder;
    private final String emailToSendFrom = "example@gmail.com";

    @Autowired
    public EmailSenderService(JavaMailSender mailSender, EmailMessageBuilder messageBuilder) {
        this.mailSender = mailSender;
        this.messageBuilder = messageBuilder;
    }

    Properties properties=new Properties();
    Session session=Session.getInstance(properties,null);

    public void sendReminders(List<User> users, Event event) throws MessagingException{
        MimeMessage message = new MimeMessage(session);
        message.setFrom(emailToSendFrom);
        message.setSubject("Påminnelse " + event.getName());

        for (User user : users) {
            message.setRecipients(Message.RecipientType.TO, user.getEmail());
            message.setText(messageBuilder.reminderMail(user, event), "UTF-8", "html");
            mailSender.send(message);
        }
    }
}
