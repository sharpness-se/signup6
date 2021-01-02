package se.accelerateit.signup6.integrationtest;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.accelerateit.signup6.dao.ParticipationMapper;
import se.accelerateit.signup6.model.Participation;
import se.accelerateit.signup6.model.ParticipationStatus;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTest.Initializer.class})
class ParticipationMapperTest extends SignupDbTest {
  static final Logger logger = LoggerFactory.getLogger(ParticipationMapperTest.class);

  private final ParticipationMapper participationMapper;

  @Autowired
  ParticipationMapperTest(ParticipationMapper participationMapper) {
    this.participationMapper = participationMapper;
  }


  @Test
  void findOneParticipation() {
    Optional<Participation> dbResponse = participationMapper.findByUserAndEvent(-4L, -1L);
    assertTrue(dbResponse.isPresent(), "could not find the participation in db");
    Participation participation = dbResponse.get();
    logger.info("participation = {}", participation);

    assertEquals(ParticipationStatus.Maybe, participation.getStatus());
    assertEquals(1, participation.getNumberOfParticipants());
    assertEquals("Får se...", participation.getComment());
    assertEquals(-4L, participation.getUserId());
    assertEquals(-1L, participation.getEventId());
    assertEquals(LocalDateTime.of(2021, 5, 1, 13, 0), participation.getSignUpTime());
  }

  @Test
  void findNoParticipation() {
    Optional<Participation> dbResponse = participationMapper.findByUserAndEvent(-4L, -2L);
    assertFalse(dbResponse.isPresent(), "found a participation that shouldn't exist");
  }

  @Test
  void createParticipation() {
    Participation participation = new Participation(ParticipationStatus.On, -1L, -2L);
    participation.setComment("Yes!!");

    int noOfRows = participationMapper.create(participation);
    logger.info("create = {}", noOfRows);

    Optional<Participation> dbResponse = participationMapper.findByUserAndEvent(-1L, -2L);
    assertTrue(dbResponse.isPresent(), "could not find the participation in db");
    participation = dbResponse.get();
    logger.info("participation = {}", participation);

    assertEquals(ParticipationStatus.On, participation.getStatus());
    assertEquals(1, participation.getNumberOfParticipants());
    assertEquals("Yes!!", participation.getComment());
    assertEquals(-1L, participation.getUserId());
    assertEquals(-2L, participation.getEventId());
  }


  @Test
  void createAndUpdateParticipation() {
    Participation participation = new Participation(ParticipationStatus.Off, -2L, -2L);
    participation.setComment("No.");

    int noOfRows = participationMapper.create(participation);
    logger.info("create = {}", noOfRows);

    Optional<Participation> dbResponse = participationMapper.findByUserAndEvent(-2L, -2L);
    assertTrue(dbResponse.isPresent(), "could not find the participation in db");
    participation = dbResponse.get();
    logger.info("participation = {}", participation);

    assertEquals(ParticipationStatus.Off, participation.getStatus());
    assertEquals(1, participation.getNumberOfParticipants());
    assertEquals("No.", participation.getComment());
    assertEquals(-2L, participation.getUserId());
    assertEquals(-2L, participation.getEventId());

    participation.setStatus(ParticipationStatus.On);
    participation.setComment("OK!!");
    participation.stampTimeNow();
    noOfRows = participationMapper.update(participation);
    logger.info("update = {}", noOfRows);

    dbResponse = participationMapper.findByUserAndEvent(-2L, -2L);
    assertTrue(dbResponse.isPresent(), "could not find the participation in db");
    participation = dbResponse.get();
    logger.info("participation = {}", participation);

    assertEquals(ParticipationStatus.On, participation.getStatus());
    assertEquals(1, participation.getNumberOfParticipants());
    assertEquals("OK!!", participation.getComment());
    assertEquals(-2L, participation.getUserId());
    assertEquals(-2L, participation.getEventId());
  }
}
