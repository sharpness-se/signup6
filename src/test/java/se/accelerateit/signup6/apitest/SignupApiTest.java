package se.accelerateit.signup6.apitest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import se.accelerateit.signup6.dao.EventMapper;
import se.accelerateit.signup6.dao.ParticipationMapper;
import se.accelerateit.signup6.dao.UserMapper;
import se.accelerateit.signup6.modelvalidator.EventValidator;

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

  protected ObjectMapper jsonMapper;

  protected SignupApiTest() {
    jsonMapper = new ObjectMapper();
    jsonMapper.findAndRegisterModules(); // to get mapping of LocalDateTime correct
  }
}
