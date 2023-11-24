package se.accelerateit.signup6.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import se.accelerateit.signup6.model.Visibility.Administrator;
import se.accelerateit.signup6.model.Visibility.Member;
import se.accelerateit.signup6.model.Visibility.Public;

@Data
public class User {
  @JsonView(Public.class) private Long id;
  @JsonView(Public.class) private String firstName;
  @JsonView(Public.class) private String lastName;
  @JsonView(Public.class) private String comment;
  @JsonView(Member.class) private String email;
  @JsonView(Member.class) private String phone;
  @JsonView(Administrator.class) private Permission permission;
  @JsonIgnore private String pwd;
  @JsonView(Public.class) private ImageProvider imageProvider;
  @JsonView(Public.class) private String imageVersion;
  @JsonView(Public.class) private String providerKey;
  @JsonIgnore private String authInfo;
}
