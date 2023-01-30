package se.accelerateit.signup6.reminder;

import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.model.ParticipationStatus;
import se.accelerateit.signup6.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class MessageBuilderTest {

  private final MessageBuilder messageBuilder;

  public MessageBuilderTest() {
    JavaMailSender mailSender = mock(JavaMailSender.class);
    FreeMarkerConfigurer freeMarkerConfigurer = mock(FreeMarkerConfigurer.class);
    messageBuilder = new MessageBuilder(mailSender, freeMarkerConfigurer);
    messageBuilder.setApplicationName("SignUpSIX");
    messageBuilder.setBackendBaseUrl("http://signup.com");
  }

  @Test
  void toHref() {
    User user = new User();
    user.setId(111L);
    Event event = new Event();
    event.setId(222L);
    String href = messageBuilder.toHref(user, event, ParticipationStatus.Maybe);
    assertEquals("http://signup.com/api/participations/registration?userId=111&eventId=222&pStatus=Maybe", href);
  }
}