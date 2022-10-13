package se.accelerateit.signup6.email.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import se.accelerateit.signup6.email.EmailSenderService;
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
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender mailSender;
    private final String emailToSendFrom = "example@gmail.com";

    @Autowired
    public EmailSenderServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /*
    @Override
    public void send(String to, String subject, String message) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailToSendFrom);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);

        this.mailSender.send(simpleMailMessage);
    }
    */

    Properties properties=new Properties();
    Session session=Session.getInstance(properties,null);

    @Override
    public void send(String to, String subject, String message) throws MessagingException {
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(emailToSendFrom);
        mimeMessage.setRecipients(Message.RecipientType.TO, to);
        mimeMessage.setSubject(subject);

        mimeMessage.setContent(EmailMessageBuilder.exmplMessage(),"text/html");

        this.mailSender.send(mimeMessage);
    }

    @Override
    public void sendReminders(List<User> users, Event event){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailToSendFrom);
        message.setSubject("Påminnelse " + event.getName());
        message.setText("Glöm ej anmäla dig till " + event.getName()
                + "Länk till sammankomst: " + "URL");

        for (User user : users) {
            message.setTo(user.getEmail());
            mailSender.send(message);
        }
    }
}
