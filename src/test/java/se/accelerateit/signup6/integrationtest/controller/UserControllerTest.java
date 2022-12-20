package se.accelerateit.signup6.integrationtest.controller;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.accelerateit.signup6.api.UserController;
import se.accelerateit.signup6.integrationtest.SignupDbTest;
import se.accelerateit.signup6.modelvalidator.UserDoesNotExistException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTest.Initializer.class})
class UserControllerTest extends SignupDbTest {
  static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

  private final UserController userController;

  @Autowired
  UserControllerTest(UserController userController) {
    this.userController = userController;
  }


  @Test
  void getExistingUser() {
    final var user = userController.find(-4L);
    logger.info("user = {}", user);

    assertEquals(-4L, user.getId());
    assertEquals("Frodo", user.getFirstName());
    assertEquals("Baggins", user.getLastName());
    assertEquals("frodo.baggins@mailinator.com", user.getEmail());
  }

  @Test
  void getNonExistingUser() {
    assertThrows(UserDoesNotExistException.class, () -> userController.find(-99999999L));
  }

}
