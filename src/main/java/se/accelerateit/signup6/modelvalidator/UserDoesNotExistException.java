package se.accelerateit.signup6.modelvalidator;

public class UserDoesNotExistException extends DataModelException {
  public UserDoesNotExistException() {
    super("User does not exist");
  }
}
