package se.accelerateit.signup6.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.accelerateit.signup6.dao.UserMapper;
import se.accelerateit.signup6.model.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoggedInUserService implements UserDetailsService {

  private final UserMapper userMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Optional<User> userInDb = userMapper.findByEmail(username);
      if (userInDb.isPresent()){
        User user = userInDb.get();
        return new LoggedInUser(user);
      } else {
        throw new UsernameNotFoundException("User not found");
      }
    }
}
