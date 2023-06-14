package se.accelerateit.signup6.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RegistrationStatus {

  @JsonView(Visibility.Public.class) public List<User> on;
  @JsonView(Visibility.Public.class) public List<User> maybe;
  @JsonView(Visibility.Public.class) public List<User> off;
  @JsonView(Visibility.Public.class) public List<User> unregistered;
}
