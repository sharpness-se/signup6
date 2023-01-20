package se.accelerateit.signup6.modelvalidator;

public class GroupDoesNotExistException extends DataModelException {
  public GroupDoesNotExistException() {
    super("Group does not exist");
  }
}
