package se.accelerateit.signup6.modelvalidator;

import org.springframework.http.HttpStatus;

public class WtfException extends DataModelException {
  public WtfException() {
    super(HttpStatus.INTERNAL_SERVER_ERROR, "This doesn't make sense. Don't know what's going on.");
  }
}
