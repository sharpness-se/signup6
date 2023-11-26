package se.accelerateit.signup6.api.filter;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;
import se.accelerateit.signup6.model.Permission;
import se.accelerateit.signup6.model.Visibility;

import java.util.Arrays;
import java.util.List;

/**
 * Check out <a href="https://www.baeldung.com/spring-security-role-filter-json">Filtering Jackson JSON Output Based on Spring Security Role</a>
 */
@RestControllerAdvice
class JsonResponseFilter extends AbstractMappingJacksonResponseBodyAdvice {

  private static final List<String> availableAuthorities = Arrays.stream(Permission.values()).map(Enum::name).toList();


  private boolean isLoggedIn(Authentication authentication) {
    if(authentication == null
       || authentication instanceof AnonymousAuthenticationToken
       || !authentication.isAuthenticated()) {
      return false;
    }
    var userAuthorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    return userAuthorities.stream().anyMatch(availableAuthorities::contains);
  }

  private boolean isAdministrator(Authentication authentication) {
    return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(Permission.Administrator.name()));
  }


  @Override
  protected void beforeBodyWriteInternal(@NonNull MappingJacksonValue bodyContainer,
                                         @NonNull MediaType contentType,
                                         @NonNull MethodParameter returnType,
                                         @NonNull ServerHttpRequest request,
                                         @NonNull ServerHttpResponse response) {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    if(isLoggedIn(authentication)) {
      if(isAdministrator(authentication)) {
        bodyContainer.setSerializationView(Visibility.Administrator.class);
      } else {
        bodyContainer.setSerializationView(Visibility.Member.class);
      }
    } else {
      bodyContainer.setSerializationView(Visibility.Public.class);
    }
  }
}