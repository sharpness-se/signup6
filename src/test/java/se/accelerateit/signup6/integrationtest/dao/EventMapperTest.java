package se.accelerateit.signup6.integrationtest.dao;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.accelerateit.signup6.dao.EventMapper;
import se.accelerateit.signup6.integrationtest.SignupDbTest;
import se.accelerateit.signup6.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
  void findOneEventById() {
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

  @Test
  void findNoEventById() {
    Optional<Event> dbResponse = eventMapper.findById(-99L);
    assertTrue(dbResponse.isEmpty(), "Found an event that should not exist");
  }

  @Test
  void findAllEventsTest() {
    List<Event> eventList = eventMapper.findAll();
    logger.info("eventList = {}", eventList);
    assertEquals(4, eventList.size());
  }

  @Test
  void insertMonsterEvent() {
    Group existingIdGroup = new Group();
    existingIdGroup.setId(-1L);
    existingIdGroup.setName("");
    existingIdGroup.setDescription("");
    existingIdGroup.setMailFrom("");
    existingIdGroup.setMailSubjectPrefix("");

    Event monster = new Event();
    monster.setId(null);
    monster.setName("Monster consumption");
    monster.setDescription("Monster drinkin innit");
    monster.setStartTime(LocalDateTime.parse("2022-12-24T14:15:01"));
    monster.setEndTime(LocalDateTime.parse("2026-12-24T23:00:01"));
    monster.setLastSignUpDate(LocalDate.parse("2021-12-13"));
    monster.setVenue("Everywhere");
    monster.setAllowExtraFriends(true);
    monster.setEventStatus(EventStatus.Created);
    monster.setMaxParticipants(null);
    monster.setCancellationReason(null);
    monster.setGroup(existingIdGroup);

    try{
      eventMapper.createEvent(monster);
    }catch (Exception e){
      e.printStackTrace();
    }


    Optional<Event> dbResponse = eventMapper.findById(1L);
    assertTrue(dbResponse.isPresent(), "could not find the user in db");
    Event event = dbResponse.get();
    logger.info("event = {}", event);

    assertEquals("Monster consumption", event.getName());
    assertEquals("Monster drinkin innit", event.getDescription());
    assertEquals("Everywhere", event.getVenue());
    assertEquals(EventStatus.Created, event.getEventStatus());
    assertTrue(event.isAllowExtraFriends());
  }

  @Test
  void findAllUpcomingEvents() {
    LocalDate testDate = LocalDate.of(2022, 1,1);
    List<Event> eventList = eventMapper.findAllUpcomingEvents(testDate);
    logger.info("eventList = {}", eventList);
    assertEquals(1, eventList.size());
  }
}
