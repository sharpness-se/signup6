package se.accelerateit.signup6.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.accelerateit.signup6.dao.EventMapper;
import se.accelerateit.signup6.dao.UserMapper;
import se.accelerateit.signup6.email.EmailSenderService;
import se.accelerateit.signup6.email.emailUtil.EmailMessageBuilder;
import se.accelerateit.signup6.model.EmailMessage;
import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.model.ParticipationStatus;
import se.accelerateit.signup6.model.User;

import javax.mail.MessagingException;

@RestController
public class EmailController extends BaseApiController{

    private final EmailSenderService emailSenderService;

    @Autowired
    public EmailController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/send-email")
    public ResponseEntity sendEmail(@RequestBody EmailMessage emailMessage) throws MessagingException {
        this.emailSenderService.send(emailMessage.getTo(), emailMessage.getSubject(), emailMessage.getMessage());
        return ResponseEntity.ok("Email sent");
    }

    @GetMapping("/test")
    public String getLink(){
        User user = new User();
        user.setId(-5L);
        Event event = new Event();
        event.setId(-2L);
        ParticipationStatus pStatus = ParticipationStatus.Maybe;

        return EmailMessageBuilder.getLink(user, event, pStatus);
    }

}
