package se.accelerateit.signup6.modelvalidator;

public class EventDoesNotExistException extends DataModelException {
  public EventDoesNotExistException() {
    super("Event does not exist");
  }
}
