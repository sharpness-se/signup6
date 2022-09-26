package se.accelerateit.signup6.integrationtest.dao;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.accelerateit.signup6.dao.UserMapper;
import se.accelerateit.signup6.integrationtest.SignupDbTest;
import se.accelerateit.signup6.model.ImageProvider;
import se.accelerateit.signup6.model.Permission;
import se.accelerateit.signup6.model.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTest.Initializer.class})
class UserMapperTest extends SignupDbTest {
  static final Logger logger = LoggerFactory.getLogger(UserMapperTest.class);

  private final UserMapper userMapper;

  @Autowired
  UserMapperTest(UserMapper userMapper) {
    this.userMapper = userMapper;
  }


  @Test
  void findAdminUser() {
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

  @Test
  void findFrodoUser() {
    Optional<User> dbResponse = userMapper.findByEmail("frodo.baggins@crisp.se");
    assertTrue(dbResponse.isPresent(), "could not find the user in db");
    User user = dbResponse.get();
    logger.info("user = {}", user);

    assertEquals("Frodo", user.getFirstName());
    assertEquals("Baggins", user.getLastName());
    assertEquals("Ringbärare", user.getComment());
    assertEquals("frodo.baggins@crisp.se", user.getEmail());
    assertEquals("", user.getPhone());
    assertEquals(Permission.NormalUser, user.getPermission());
    assertNotNull(user.getPwd());
    assertEquals(ImageProvider.Gravatar, user.getImageProvider());
  }

  @Test
  void insertGoblinUser(){
    User goblin = User.builder()
            .id(null)
            .firstName("Goblin")
            .lastName("Boblin")
            .comment("")
            .email("Goblin@gob.com")
            .phone("")
            .permission(Permission.NormalUser)
            .pwd("*")
            .imageProvider(ImageProvider.Gravatar)
            .imageVersion(null)
            .providerKey(null)
            .authInfo(null)
            .build();

    try{
      userMapper.insertUser(goblin);
    }catch (Exception e){
      e.printStackTrace();
    }


    Optional<User> dbResponse = userMapper.findByEmail("Goblin@gob.com");
    assertTrue(dbResponse.isPresent(), "could not find the user in db");
    User user = dbResponse.get();
    logger.info("user = {}", user);

    assertEquals("Goblin", user.getFirstName());
    assertEquals("Boblin", user.getLastName());
    assertEquals("", user.getComment());
    assertEquals("Goblin@gob.com", user.getEmail());
    assertEquals("", user.getPhone());
  }

}
