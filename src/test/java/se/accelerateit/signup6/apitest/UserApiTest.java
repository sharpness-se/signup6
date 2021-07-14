package se.accelerateit.signup6.apitest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import se.accelerateit.signup6.dao.ParticipationMapper;
import se.accelerateit.signup6.dao.UserMapper;
import se.accelerateit.signup6.model.ImageProvider;
import se.accelerateit.signup6.model.Permission;
import se.accelerateit.signup6.model.User;
import se.accelerateit.signup6.modelvalidator.EventValidator;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class UserApiTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private EventValidator eventValidator;

  @MockBean
  private ParticipationMapper participationMapper;

  @MockBean
  private UserMapper userMapper;

  private final ObjectMapper jsonMapper;

  public UserApiTest() {
    jsonMapper = new ObjectMapper();
    jsonMapper.findAndRegisterModules(); // to get mapping of LocalDateTime correct
  }


  @Test
  public void getExistingUser() throws Exception {
    Long userId = 99L;

    User user = new User();
    user.setId(userId);
    user.setFirstName("Hugo");
    user.setLastName("Boss");
    user.setComment("A guy with a suite");
    user.setEmail("hugo@boss.suite");
    user.setPhone("1234567890");
    user.setPermission(Permission.NormalUser);
    user.setPwd("secretpassword");
    user.setImageProvider(ImageProvider.Gravatar);
    user.setImageVersion("v1.0");
    user.setProviderKey("a key");
    user.setAuthInfo("secret stuff");


    Mockito.when(userMapper.findById(userId)).thenReturn(Optional.of(user));

    var result = mockMvc.perform(get("/api/users/" + userId)).andExpect(status().isOk());

    // TODO: verify other visibilities than Visibility.Public
    result
      .andExpect(jsonPath("$.id", Matchers.equalTo(userId.intValue())))
      .andExpect(jsonPath("$.firstName", Matchers.equalTo(user.getFirstName())))
      .andExpect(jsonPath("$.lastName", Matchers.equalTo(user.getLastName())))
      .andExpect(jsonPath("$.comment", Matchers.equalTo(user.getComment())))
      .andExpect(jsonPath("$.imageProvider", Matchers.equalTo(user.getImageProvider().toString())))
      .andExpect(jsonPath("$.imageVersion", Matchers.equalTo(user.getImageVersion())))
      .andExpect(jsonPath("$.providerKey", Matchers.equalTo(user.getProviderKey())))
      .andExpect(jsonPath("$.pwd").doesNotExist())
      .andExpect(jsonPath("$.permission").doesNotExist())
      .andExpect(jsonPath("$.authInfo").doesNotExist());
  }

  @Test
  public void getNonExistingUser() throws Exception {
    Long userId = 88L;

    Mockito.when(userMapper.findById(userId)).thenReturn(Optional.empty());

    mockMvc.perform(get("/api/users/" + userId))
      .andExpect(status().isNotFound())
      .andExpect(content().string("User does not exist"));
  }
}
