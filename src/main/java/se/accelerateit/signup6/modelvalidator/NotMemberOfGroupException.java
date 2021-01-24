package se.accelerateit.signup6.modelvalidator;

public class NotMemberOfGroupException extends DataModelException {
  public NotMemberOfGroupException() {
    super("User is not member of required group");
  }
}
