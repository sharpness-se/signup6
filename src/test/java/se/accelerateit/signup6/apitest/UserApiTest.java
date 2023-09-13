package se.accelerateit.signup6.apitest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.Import;
import se.accelerateit.signup6.model.ImageProvider;
import se.accelerateit.signup6.model.Permission;
import se.accelerateit.signup6.model.User;
import se.accelerateit.signup6.security.config.SecurityConfiguration;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Import(SecurityConfiguration.class)
public class UserApiTest extends SignupApiTest {

  @Test
  public void getExistingUser() throws Exception {
    Long userId = 99L;

    User user = new User();
    user.setId(userId);
    user.setFirstName("Goblin");
    user.setLastName("Boblin");
    user.setComment("");
    user.setEmail("Goblin@gob.com");
    user.setPhone("");
    user.setPermission(Permission.NormalUser);
    user.setPwd("*");
    user.setImageProvider(ImageProvider.Gravatar);
    user.setImageVersion(null);
    user.setProviderKey(null);
    user.setAuthInfo(null);


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
