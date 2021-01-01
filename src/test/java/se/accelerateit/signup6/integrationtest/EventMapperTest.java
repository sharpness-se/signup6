package se.accelerateit.signup6.integrationtest;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.accelerateit.signup6.dao.EventMapper;
import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.model.EventStatus;
import se.accelerateit.signup6.model.Group;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTest.Initializer.class})
class EventMapperTest extends SignupDbTest {
  static final Logger logger = LoggerFactory.getLogger(EventMapperTest.class);

  private final EventMapper eventMapper;

  @Autowired
  EventMapperTest(EventMapper eventMapper) {
    this.eventMapper = eventMapper;
  }


  @Test
  void findOneEvent() {
    Optional<Event> dbResponse = eventMapper.findById(-1L);
    assertTrue(dbResponse.isPresent(), "could not find the event in db");
    Event event = dbResponse.get();
    logger.info("event = {}", event);

    assertEquals("Crisp RD", event.getName());
    assertEquals("Vad jag lärde mig av BigFamilyTrip", event.getDescription());
    assertEquals(LocalDateTime.of(2021, 5, 3, 18, 0), event.getStartTime());
    assertEquals(LocalDateTime.of(2021, 5, 3, 19, 0), event.getEndTime());
    assertEquals(LocalDate.of(2021, 5, 2), event.getLastSignUpDate());
    assertEquals("Crisp Office", event.getVenue());
    assertFalse(event.isAllowExtraFriends());
    assertEquals(EventStatus.Created, event.getEventStatus());
    assertNull(event.getMaxParticipants());
    assertNull(event.getCancellationReason());

    Group group = event.getGroup();
    assertEquals("Crisp Rocket Days", group.getName());
    assertEquals("För dej som vill lära dej mer", group.getDescription());
    assertEquals("", group.getMailFrom());
    assertEquals("Crisp Rocket Days", group.getMailSubjectPrefix());
  }
}
