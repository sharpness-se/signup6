package se.accelerateit.signup6.apitest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import se.accelerateit.signup6.security.config.SecurityConfiguration;

@WebMvcTest
@ContextConfiguration(classes = SecurityConfiguration.class)
public abstract class SignupApiTest {

  @Autowired
  protected MockMvc mockMvc;

  protected final ObjectMapper jsonMapper;

  protected SignupApiTest() {
    jsonMapper = new ObjectMapper();
    jsonMapper.findAndRegisterModules(); // to get mapping of LocalDateTime correct
  }
}
