package se.accelerateit.signup6.api.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import se.accelerateit.signup6.model.Permission;
import se.accelerateit.signup6.model.User;
import se.accelerateit.signup6.model.Visibility;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonResponseFilterTest {


  MediaType contentType = Mockito.mock(MediaType.class);
  MethodParameter returnType = Mockito.mock(MethodParameter.class);
  ServerHttpRequest request = Mockito.mock(ServerHttpRequest.class);
  ServerHttpResponse response = Mockito.mock(ServerHttpResponse.class);

  private User createAdminUser() {
    User user = new User();
    user.setId(1L);
    user.setEmail("admin@mailinator.com");
    user.setFirstName("Admin");
    user.setLastName("Dummisson");
    user.setPermission(Permission.Administrator);
    user.setPhone("1234567890");
    user.setPwd("admin");
    return user;
  }

  private User createNormalUser() {
    User user = new User();
    user.setId(2L);
    user.setEmail("normal@mailinator.com");
    user.setFirstName("Normal");
    user.setLastName("Dummisson");
    user.setPermission(Permission.NormalUser);
    user.setPhone("1234567890");
    user.setPwd("normal");
    return user;
  }

  public void fakeLoginAs(User user) {
    var authentication = new UsernamePasswordAuthenticationToken(
      user.getEmail(),
      user.getPwd(),
      List.of(new SimpleGrantedAuthority(user.getPermission().name())));

    authentication.setDetails(user);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
  }

  @BeforeEach
  public void fakeLogout() {
    SecurityContextHolder.clearContext();
  }


  @Test
  void notLoggedInGivesPublic() {
    User toSerialize = createAdminUser();
    MappingJacksonValue bodyContainer = new MappingJacksonValue(toSerialize);

    JsonResponseFilter jsonResponseFilter = new JsonResponseFilter();
    jsonResponseFilter.beforeBodyWriteInternal(bodyContainer, contentType, returnType, request, response);

    var view = bodyContainer.getSerializationView();
    assertEquals(Visibility.Public.class, view);
  }

  @Test
  void loggedInAsAdminGivesAdministrator() {
    User admin = createAdminUser();
    fakeLoginAs(admin);

    User toSerialize = createNormalUser();
    MappingJacksonValue bodyContainer = new MappingJacksonValue(toSerialize);

    JsonResponseFilter jsonResponseFilter = new JsonResponseFilter();
    jsonResponseFilter.beforeBodyWriteInternal(bodyContainer, contentType, returnType, request, response);

    var view = bodyContainer.getSerializationView();
    assertEquals(Visibility.Administrator.class, view);
  }

  @Test
  void loggedInAsNormalGivesMember() {
    User user = createNormalUser();
    fakeLoginAs(user);

    User toSerialize = createAdminUser();
    MappingJacksonValue bodyContainer = new MappingJacksonValue(toSerialize);

    JsonResponseFilter jsonResponseFilter = new JsonResponseFilter();
    jsonResponseFilter.beforeBodyWriteInternal(bodyContainer, contentType, returnType, request, response);

    var view = bodyContainer.getSerializationView();
    assertEquals(Visibility.Member.class, view);
  }

}