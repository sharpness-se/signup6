package se.accelerateit.signup6.integrationtest.controller;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.accelerateit.signup6.api.ParticipationController;
import se.accelerateit.signup6.integrationtest.SignupDbTest;
import se.accelerateit.signup6.model.Participation;
import se.accelerateit.signup6.model.ParticipationStatus;
import se.accelerateit.signup6.modelvalidator.NotMemberOfGroupException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTest.Initializer.class})
class ParticipationControllerTest extends SignupDbTest {
  static final Logger logger = LoggerFactory.getLogger(ParticipationControllerTest.class);

  private final ParticipationController participationController;

  @Autowired
  ParticipationControllerTest(ParticipationController participationController) {
    this.participationController = participationController;
  }


  @Test
  void getExistingParticipation() {
    final var participation = participationController.find(-4L, -1L);
    logger.info("participation = {}", participation);

    assertEquals(ParticipationStatus.Maybe, participation.getStatus());
    assertEquals(1, participation.getNumberOfParticipants());
    assertEquals("Får se...", participation.getComment());
    assertEquals(-4L, participation.getUserId());
    assertEquals(-1L, participation.getEventId());
    assertEquals(LocalDateTime.of(2021, 5, 1, 13, 0), participation.getSignUpTime());
  }

  @Test
  void getNonExistingParticipation() {
    assertThrows(NotMemberOfGroupException.class, () -> participationController.find(-1L, -3L));
  }

  @Test
  void createAndUpdateParticipation() {
    Participation participation = new Participation(ParticipationStatus.Off, -3L, -3L);
    participation.setComment("No.");


    // should create a participation
    participation = participationController.updateOrCreate(participation);
    final var participationId = participation.getId();
    logger.info("participation = {}", participation);

    assertEquals(ParticipationStatus.Off, participation.getStatus());
    assertEquals(1, participation.getNumberOfParticipants());
    assertEquals("No.", participation.getComment());
    assertEquals(-3L, participation.getUserId());
    assertEquals(-3L, participation.getEventId());


    participation.setStatus(ParticipationStatus.On);
    participation.setComment("OK!!");
    participation.stampTimeNow();


    // should update the now exiting participation
    participation = participationController.updateOrCreate(participation);
    logger.info("participation = {}", participation);

    assertEquals(participationId, participation.getId());
    assertEquals(ParticipationStatus.On, participation.getStatus());
    assertEquals(1, participation.getNumberOfParticipants());
    assertEquals("OK!!", participation.getComment());
    assertEquals(-3L, participation.getUserId());
    assertEquals(-3L, participation.getEventId());
  }

  @Test
  void updateInvalidParticipation() {
    Participation participation = new Participation(ParticipationStatus.Off, -1L, -3L);
    assertThrows(NotMemberOfGroupException.class, () -> participationController.updateOrCreate(participation));
  }

}
