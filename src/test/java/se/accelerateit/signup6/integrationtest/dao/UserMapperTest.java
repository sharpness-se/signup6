package se.accelerateit.signup6.integrationtest.dao;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.accelerateit.signup6.dao.EventMapper;
import se.accelerateit.signup6.dao.UserMapper;
import se.accelerateit.signup6.integrationtest.SignupDbTest;
import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.model.ImageProvider;
import se.accelerateit.signup6.model.ParticipationStatus;
import se.accelerateit.signup6.model.Permission;
import se.accelerateit.signup6.model.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTest.Initializer.class})
class UserMapperTest extends SignupDbTest {
  static final Logger logger = LoggerFactory.getLogger(UserMapperTest.class);

  private final UserMapper userMapper;
  private final EventMapper eventMapper;

  @Autowired
  UserMapperTest(UserMapper userMapper, EventMapper eventMapper) {
    this.userMapper = userMapper;
    this.eventMapper = eventMapper;
  }


  @Test
  void findAdminUserByEmail() {
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
  void findFrodoUserByEmail() {
    Optional<User> dbResponse = userMapper.findByEmail("frodo.baggins@mailinator.com");
    assertTrue(dbResponse.isPresent(), "could not find the user in db");
    User user = dbResponse.get();
    logger.info("user = {}", user);

    assertEquals("Frodo", user.getFirstName());
    assertEquals("Baggins", user.getLastName());
    assertEquals("Ringbärare", user.getComment());
    assertEquals("frodo.baggins@mailinator.com", user.getEmail());
    assertEquals("", user.getPhone());
    assertEquals(Permission.NormalUser, user.getPermission());
    assertNotNull(user.getPwd());
    assertEquals(ImageProvider.Gravatar, user.getImageProvider());
  }

  @Test
  void findUserById(){
    Optional<User> dbResponse = userMapper.findById(-5L);
    assertTrue(dbResponse.isPresent(), "Ya boi doesn't exist in db");
    User user = dbResponse.get();
    logger.info("user = {}", user);

    assertEquals("John", user.getFirstName());
    assertEquals("Doe", user.getLastName());
    assertEquals("john.doe@mailinator.com", user.getEmail());
    assertEquals(Permission.NormalUser, user.getPermission());
    assertEquals(ImageProvider.Gravatar, user.getImageProvider());
  }

  @Test
  void findNoUserById(){
    Optional<User> dbResponse = userMapper.findById(-99L);
    assertTrue(dbResponse.isEmpty());
  }

  @Test
  void findNoUserByEmail(){
    Optional<User> dbResponse = userMapper.findByEmail("Oogabooga@testmail.test");
    assertTrue(dbResponse.isEmpty());
  }

  @Test
  void insertUser(){
    User goblin = new User();
    goblin.setId(null);
    goblin.setFirstName("Goblin");
    goblin.setLastName("Boblin");
    goblin.setComment("");
    goblin.setEmail("Goblin@gob.com");
    goblin.setPhone("");
    goblin.setPermission(Permission.NormalUser);
    goblin.setPwd("*");
    goblin.setImageProvider(ImageProvider.Gravatar);
    goblin.setImageVersion(null);
    goblin.setProviderKey(null);
    goblin.setAuthInfo(null);

    try{
      userMapper.createUser(goblin);
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

  @Test
  void findAllUsersTest() {
    List<User> userList = userMapper.findAll();
    assertFalse(userList.isEmpty());
    assertEquals(8, userList.size());
  }

  @Test
  void findUnregisteredMembersTest() {
    Optional<Event> event = eventMapper.findById(-9L);
    List<User> userList = userMapper.findUnregisteredMembers(event.orElseThrow());
    assertFalse(userList.isEmpty());
    assertEquals(1, userList.size());
  }

  @Test
  void findMembersByStatus2Test(){
    Optional<Event> event = eventMapper.findById(-9L);

    List<User> userList = userMapper.findMembersByStatus(ParticipationStatus.Maybe, event.orElseThrow());
    assertFalse(userList.isEmpty());
    assertEquals(1, userList.size());
  }
}
