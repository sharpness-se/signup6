package se.accelerateit.signup6.api;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import se.accelerateit.signup6.dao.UserMapper;
import se.accelerateit.signup6.model.ImageProvider;
import se.accelerateit.signup6.model.User;
import se.accelerateit.signup6.modelvalidator.UserDoesNotExistException;

@RestController
public class UserController extends BaseApiController {
  private final UserMapper userMapper;

  @Autowired
  UserController(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  @GetMapping("/users/{userId}")
  public User find(@PathVariable(value = "userId") Long userId) {
    final var result = userMapper.findById(userId);
    if(result.isPresent()) {
      User user = result.get();
      if(user.getImageProvider() == ImageProvider.Gravatar) {
        user.setImageVersion(gravatarHash(user.getEmail()));
      }
      return result.get();
    } else {
        throw new UserDoesNotExistException();
    }
  }

  String gravatarHash(String email) {
    return DigestUtils.md5Hex(email.trim().toLowerCase());
  }
}
