package se.accelerateit.signup6.apitest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import se.accelerateit.signup6.api.LogEntryController;
import se.accelerateit.signup6.api.ReminderController;
import se.accelerateit.signup6.dao.EventMapper;
import se.accelerateit.signup6.dao.GroupMapper;
import se.accelerateit.signup6.dao.MembershipMapper;
import se.accelerateit.signup6.dao.ParticipationMapper;
import se.accelerateit.signup6.dao.ReminderMapper;
import se.accelerateit.signup6.dao.UserMapper;
import se.accelerateit.signup6.modelvalidator.EventValidator;
import se.accelerateit.signup6.reminder.ReminderSenderService;
import se.accelerateit.signup6.security.auth.AuthenticationController;

@WebMvcTest
public abstract class SignupApiTest {

  @Autowired
  protected MockMvc mockMvc;

  @MockBean
  protected EventValidator eventValidator;

  @MockBean
  protected ParticipationMapper participationMapper;

  @MockBean
  protected UserMapper userMapper;

  @MockBean
  protected EventMapper eventMapper;

  @MockBean
  protected GroupMapper groupMapper;

  @MockBean
  protected MembershipMapper membershipMapper;

  @MockBean
  protected ReminderMapper reminderMapper;

  @MockBean
  protected ReminderSenderService reminderSenderService;

  @MockBean
  protected LogEntryController logEntryController;

  @MockBean
  protected ReminderController reminderController;

  @MockBean
  protected AuthenticationController authenticationController;

  protected final ObjectMapper jsonMapper;

  protected SignupApiTest() {
    jsonMapper = new ObjectMapper();
    jsonMapper.findAndRegisterModules(); // to get mapping of LocalDateTime correct
  }
}
