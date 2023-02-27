package se.accelerateit.signup6.reminder;

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
import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.model.ParticipationStatus;
import se.accelerateit.signup6.model.User;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URI;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class MessageBuilder {
  private final JavaMailSender mailSender;

  private final FreeMarkerConfigurer freemarkerConfigurer;

  @Value("${signup.application.name}")
  private String applicationName;


  @Value("${signup.backend.base.url}")
  private String backendBaseUrl;

  public void setApplicationName(String applicationName) {
    this.applicationName = applicationName;
  }

  public void setBackendBaseUrl(String backendBaseUrl) {
    this.backendBaseUrl = backendBaseUrl;
  }

  @Autowired
  public MessageBuilder(JavaMailSender mailSender, FreeMarkerConfigurer freeMarkerConfigurer) {
    this.mailSender = mailSender;
    this.freemarkerConfigurer = freeMarkerConfigurer;
  }

  public MimeMessage createReminderMessage(String to, String subject, User user, Event event)
    throws IOException, TemplateException, MessagingException {

    log.debug("Creating Reminder message for " + to);
    Map<String, Object> templateModel = createTemplateModel(user, event);
    Template freemarkerTemplate = freemarkerConfigurer.getConfiguration().getTemplate("reminderMail.ftlh");
    String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);

    return createHtmlMessage(to, subject, htmlBody);
  }


   String toHref(User user, Event event, ParticipationStatus participationStatus) {
    URI uri = WebMvcLinkBuilder
      .linkTo(WebMvcLinkBuilder.methodOn(ParticipationController.class).registerToEvent(user.getId(), event.getId(), participationStatus))
      .toUri();
    return backendBaseUrl + uri.getPath() + "?" + uri.getQuery();
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

    templateModel.put("onHref", toHref(user, event, ParticipationStatus.On));
    templateModel.put("maybeHref", toHref(user, event, ParticipationStatus.Maybe));
    templateModel.put("offHref", toHref(user, event, ParticipationStatus.Off));

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

}
