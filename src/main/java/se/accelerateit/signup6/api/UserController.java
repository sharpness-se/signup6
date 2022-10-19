package se.accelerateit.signup6.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.accelerateit.signup6.dao.UserMapper;
import se.accelerateit.signup6.model.ParticipationStatus;
import se.accelerateit.signup6.model.User;
import se.accelerateit.signup6.modelvalidator.UserDoesNotExistException;

import java.util.List;

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
      return result.get();
    } else {
        throw new UserDoesNotExistException();
    }
  }
}
