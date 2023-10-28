package se.accelerateit.signup6.modelvalidator;

import org.springframework.http.HttpStatus;

public class NotMemberOfGroupException extends DataModelException {
  public NotMemberOfGroupException() {
    super(HttpStatus.NOT_FOUND, "User is not member of required group");
  }
}
