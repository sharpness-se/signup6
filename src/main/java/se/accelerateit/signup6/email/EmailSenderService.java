package se.accelerateit.signup6.email;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import se.accelerateit.signup6.api.ParticipationController;
import se.accelerateit.signup6.email.emailUtil.MockMailSender;
import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.model.ParticipationStatus;
import se.accelerateit.signup6.model.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URI;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class EmailSenderService {
  private final JavaMailSender mailSender;

  private final MockMailSender mockMailSender;

  private final FreeMarkerConfigurer freemarkerConfigurer;

  @Value("${signup.activate.mock.email}")
  private boolean mockEmailActivated;

  @Value("${signup.application.name}")
  private String applicationName;

  @Value("${signup.base.url}")
  private String baseUrl;

  @Autowired
  public EmailSenderService(JavaMailSender mailSender, MockMailSender mockMailSender, FreeMarkerConfigurer freeMarkerConfigurer) {
    this.mailSender = mailSender;
    this.mockMailSender = mockMailSender;
    this.freemarkerConfigurer = freeMarkerConfigurer;
  }


  private MimeMessage createReminderMessage(String to, String subject, Map<String, Object> templateModel)
    throws IOException, TemplateException, MessagingException {

    log.debug("Creating Reminder message for " + to);
    Template freemarkerTemplate = freemarkerConfigurer.getConfiguration().getTemplate("reminderMail.ftlh");
    String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);

    return createHtmlMessage(to, subject, htmlBody);
  }


  private String toHref(URI uri) {
    return baseUrl + uri.getPath() + "?" + uri.getQuery();
  }

  Map<String, Object> createTemplateModel(User user, Event event) {
    Map<String, Object> templateModel = new HashMap<>();
    templateModel.put("applicationName", applicationName);
    templateModel.put("user", user);
    templateModel.put("group", event.getGroup());
    templateModel.put("event", event);

    DateTimeFormatter justDate = DateTimeFormatter.ofPattern("EEEE yyyy-MM-dd");
    DateTimeFormatter justTime = DateTimeFormatter.ofPattern("HH:mm");

    templateModel.put("formattedEventStartDate", event.getStartTime().format(justDate));
    templateModel.put("formattedEventStartTime", event.getStartTime().format(justTime));
    templateModel.put("formattedEventEndTime", event.getEndTime().format(justTime));
    templateModel.put("formattedLastSignUpDate", event.getLastSignUpDate().format(justDate));

    URI onUri = WebMvcLinkBuilder
      .linkTo(WebMvcLinkBuilder.methodOn(ParticipationController.class).registerToEvent(user.getId(), event.getId(), ParticipationStatus.On))
      .toUri();
    templateModel.put("onHref", toHref(onUri));

    URI maybeUri = WebMvcLinkBuilder
      .linkTo(WebMvcLinkBuilder.methodOn(ParticipationController.class).registerToEvent(user.getId(), event.getId(), ParticipationStatus.Maybe))
      .toUri();
    templateModel.put("maybeHref", toHref(maybeUri));

    URI offUri = WebMvcLinkBuilder
      .linkTo(WebMvcLinkBuilder.methodOn(ParticipationController.class).registerToEvent(user.getId(), event.getId(), ParticipationStatus.Off))
      .toUri();
    templateModel.put("offHref", toHref(offUri));


    return templateModel;
  }

  private MimeMessage createHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(htmlBody, true);
    return message;
  }

  public void sendReminders(List<User> users, Event event) throws MessagingException, TemplateException, IOException {
    log.debug("Have {} users to remind about the event '{}'", users.size(), event.getName());
    for (User user : users) {
      log.info("Sending reminder to " + user.getEmail());
      if (!mockEmailActivated) {
        mailSender.send(createReminderMessage(user.getEmail(), "Påminnelse " + event.getName(), createTemplateModel(user, event)));
      } else {
        mockMailSender.send(createReminderMessage(user.getEmail(), "Påminnelse " + event.getName(), createTemplateModel(user, event)));
      }
    }
  }
}
