package se.accelerateit.signup6.model;

import lombok.Data;

@Data
public class User {
  private Long id;
  private String firstName;
  private String lastName;
  private String comment;
  private String email;
  private String phone;
  private Permission permission;
  private String pwd;
  private ImageProvider imageProvider;
  private String imageVersion;
  private String providerKey;
  private String authInfo;
}
