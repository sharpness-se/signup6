package se.accelerateit.signup6.integrationtest.controller;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.accelerateit.signup6.api.EventController;
import se.accelerateit.signup6.api.UserController;
import se.accelerateit.signup6.integrationtest.SignupDbTest;
import se.accelerateit.signup6.modelvalidator.EventDoesNotExistException;
import se.accelerateit.signup6.modelvalidator.UserDoesNotExistException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTest.Initializer.class})
class EventControllerTest extends SignupDbTest {
  static final Logger logger = LoggerFactory.getLogger(EventControllerTest.class);

  private final EventController eventController;

  @Autowired
  EventControllerTest(EventController eventController) {
    this.eventController = eventController;
  }


  @Test
  void getExistingEvent() {
    final var event = eventController.find(-2L);
    logger.info("event = {}", event);

    assertEquals(-2L, event.getId());
    assertEquals("Crisp RD", event.getName());
    assertEquals("Scala 3.0 och Play 3.0", event.getDescription());

    assertEquals(-1L, event.getGroup().getId());
    assertEquals("Crisp Rocket Days", event.getGroup().getName());
    assertEquals("För dej som vill lära dej mer", event.getGroup().getDescription());
  }

  @Test
  void getNonExistingEvent() {
    assertThrows(EventDoesNotExistException.class, () -> eventController.find(-99999999L));
  }

}
