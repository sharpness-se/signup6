package se.accelerateit.signup6.dao;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.accelerateit.signup6.model.ImageProvider;
import se.accelerateit.signup6.model.Permission;
import se.accelerateit.signup6.model.User;

import java.util.Optional;


@SpringBootTest
class UserMapperTest {
  Logger logger = LoggerFactory.getLogger(UserMapperTest.class);

  private final UserMapper userMapper;

  @Autowired
  UserMapperTest(UserMapper userMapper) {
    this.userMapper = userMapper;
  }


  @Test
  void findOneUser() {
    Optional<User> dbResponse = userMapper.findByEmail("admin@crisp.se");
    assertTrue(dbResponse.isPresent(), "could not find the user in db");
    User user = dbResponse.get();
    logger.info("user = {}", user);

    assertEquals("Admin", user.getFirstName());
    assertEquals("Istratör", user.getLastName());
    assertEquals("Administratör för SignUp", user.getComment());
    assertEquals("admin@crisp.se", user.getEmail());
    assertEquals("08-55695015", user.getPhone());
    assertEquals(Permission.Administrator, user.getPermission());
    assertNotNull(user.getPwd());
    assertEquals(ImageProvider.Gravatar, user.getImageProvider());
  }

}
