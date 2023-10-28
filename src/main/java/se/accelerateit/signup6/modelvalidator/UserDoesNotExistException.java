package se.accelerateit.signup6.modelvalidator;

import org.springframework.http.HttpStatus;

public class UserDoesNotExistException extends DataModelException {
  public UserDoesNotExistException() {
    super(HttpStatus.NOT_FOUND, "User does not exist");
  }
}
