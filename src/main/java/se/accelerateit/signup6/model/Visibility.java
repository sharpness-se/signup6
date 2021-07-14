package se.accelerateit.signup6.model;

public class Visibility {
  public interface Public {}
  public interface Personal extends Public {}
  public interface Administrator extends Personal {}
}
