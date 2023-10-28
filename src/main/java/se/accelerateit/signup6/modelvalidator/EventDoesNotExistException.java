package se.accelerateit.signup6.modelvalidator;

import org.springframework.http.HttpStatus;

public class EventDoesNotExistException extends DataModelException {
  public EventDoesNotExistException() {
    super(HttpStatus.NOT_FOUND, "Event does not exist");
  }
}
