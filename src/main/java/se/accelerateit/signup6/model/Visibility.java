package se.accelerateit.signup6.model;

public interface Visibility {
  interface Public {}
  interface Member extends Public {}
  interface Administrator extends Member {}
}
