package se.accelerateit.signup6.api;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;
import se.accelerateit.signup6.model.Visibility;

/**
 * Check out <a href="https://www.baeldung.com/spring-security-role-filter-json">Filtering Jackson JSON Output Based on Spring Security Role</a>
 */
@RestControllerAdvice
class SecurityJsonViewControllerAdvice extends AbstractMappingJacksonResponseBodyAdvice {

  @Override
  protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer,
                                         @NonNull MediaType contentType,
                                         @NonNull MethodParameter returnType,
                                         @NonNull ServerHttpRequest request,
                                         @NonNull ServerHttpResponse response) {
    bodyContainer.setSerializationView(Visibility.Public.class);
  }

}