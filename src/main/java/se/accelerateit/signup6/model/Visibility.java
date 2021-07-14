package se.accelerateit.signup6.model;

public interface Visibility {
  interface Public {}
  interface Personal extends Public {}
  interface Administrator extends Personal {}
}
