package se.accelerateit.signup6.integrationtest.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.accelerateit.signup6.api.UserController;
import se.accelerateit.signup6.integrationtest.SignupDbTest;
import se.accelerateit.signup6.model.ImageProvider;
import se.accelerateit.signup6.modelvalidator.UserDoesNotExistException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Slf4j
@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTest.Initializer.class})
class UserControllerTest extends SignupDbTest {
  private final UserController userController;

  @Autowired
  UserControllerTest(UserController userController) {
    this.userController = userController;
  }


  @Test
  void getExistingUser() {
    final var user = userController.find(-4L);
    log.info("user = {}", user);

    assertEquals(-4L, user.getId());
    assertEquals("Frodo", user.getFirstName());
    assertEquals("Baggins", user.getLastName());
    assertEquals("frodo.baggins@mailinator.com", user.getEmail());
  }

  @Test
  void getGravatarUser() {
    final var user = userController.find(-1L);
    log.info("user = {}", user);

    assertEquals(-1L, user.getId());
    assertEquals(ImageProvider.Gravatar, user.getImageProvider());
    assertEquals("567928b4d76658f761e0a5ee58135ec5", user.getImageVersion());
  }

  @Test
  void getNonExistingUser() {
    assertThrows(UserDoesNotExistException.class, () -> userController.find(-99999999L));
  }

}
