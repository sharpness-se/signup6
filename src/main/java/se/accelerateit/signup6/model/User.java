package se.accelerateit.signup6.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@Data
public class User {
  @JsonView(Visibility.Public.class) private Long id;
  @JsonView(Visibility.Public.class) private String firstName;
  @JsonView(Visibility.Public.class) private String lastName;
  @JsonView(Visibility.Public.class) private String comment;
  @JsonView(Visibility.Personal.class) private String email;
  @JsonView(Visibility.Personal.class) private String phone;
  @JsonView(Visibility.Administrator.class) private Permission permission;
  @JsonIgnore private String pwd;
  @JsonView(Visibility.Public.class) private ImageProvider imageProvider;
  @JsonView(Visibility.Public.class) private String imageVersion;
  @JsonView(Visibility.Public.class) private String providerKey;
  @JsonIgnore private String authInfo;
}
