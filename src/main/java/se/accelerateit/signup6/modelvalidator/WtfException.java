package se.accelerateit.signup6.modelvalidator;

public class WtfException extends DataModelException {
  public WtfException() {
    super("This doesn't make sense. Don't know what's going on.");
  }
}
