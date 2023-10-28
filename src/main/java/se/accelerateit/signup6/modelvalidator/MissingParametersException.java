package se.accelerateit.signup6.modelvalidator;

import org.springframework.http.HttpStatus;

public class MissingParametersException extends DataModelException {
  public MissingParametersException() {
    super(HttpStatus.BAD_REQUEST, "Missing parameters");
  }
}
