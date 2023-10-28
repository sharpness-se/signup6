package se.accelerateit.signup6.modelvalidator;

import org.springframework.http.HttpStatus;

public class GroupDoesNotExistException extends DataModelException {
  public GroupDoesNotExistException() {
    super(HttpStatus.NOT_FOUND, "Group does not exist");
  }
}
