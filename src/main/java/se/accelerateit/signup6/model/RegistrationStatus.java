package se.accelerateit.signup6.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegistrationStatus {

  @JsonView(Visibility.Public.class) public int onCounter = 0;
  @JsonView(Visibility.Public.class) public int maybeCounter = 0;
  @JsonView(Visibility.Public.class) public int offCounter = 0;
  @JsonView(Visibility.Public.class) public int unregisteredCounter = 0;
}
