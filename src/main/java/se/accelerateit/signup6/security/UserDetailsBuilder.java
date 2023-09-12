package se.accelerateit.signup6.security;


import org.springframework.security.core.userdetails.UserDetails;
import se.accelerateit.signup6.model.SecUser;
import se.accelerateit.signup6.model.User;

public class UserDetailsBuilder {

  public UserDetails buildUserDetails(User user) {

    return new SecUser(
      user.getId(),
      user.getFirstName(),
      user.getEmail(),
      user.getPwd(),
      user.getPermission()
      );
  }

}
