package se.accelerateit.signup6.integrationtest.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.accelerateit.signup6.dao.EventMapper;
import se.accelerateit.signup6.integrationtest.SignupDbTest;
import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.model.EventStatus;
import se.accelerateit.signup6.model.Group;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Slf4j
@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTest.Initializer.class})
class EventMapperTest extends SignupDbTest {
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
    log.info("event = {}", event);

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
    log.info("eventList = {}", eventList);
    assertEquals(6, eventList.size());
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

    eventMapper.createEvent(monster);


    Optional<Event> dbResponse = eventMapper.findById(1L);
    assertTrue(dbResponse.isPresent(), "could not find the user in db");
    Event event = dbResponse.get();
    log.info("event = {}", event);

    assertEquals("Monster consumption", event.getName());
    assertEquals("Monster drinkin innit", event.getDescription());
    assertEquals("Everywhere", event.getVenue());
    assertEquals(EventStatus.Created, event.getEventStatus());
    assertTrue(event.isAllowExtraFriends());
  }

  @Test
  void findAllUpcomingEvents() {
    LocalDate testDate = LocalDate.of(2028, 1,1);
    List<Event> eventList = eventMapper.findAllUpcomingEvents(testDate);
    Event futureEvent = eventList.get(0);
    log.info("eventList = {}", eventList);

    assertEquals(1, eventList.size());
    assertEquals(-67, futureEvent.getId());
    assertEquals("EventUnitTest2", futureEvent.getName());
  }

  @Test
  void findAllEventsByGroup() {
    List<Event> eventList = eventMapper.findAllEventsByGroup(-59);
    Event futureEvent = eventList.get(0);
    Event pastEvent = eventList.get(1);
    log.info("eventList = {}", eventList);

    assertEquals(2, eventList.size());
    assertNotEquals(1, eventList.size());

    assertEquals("UnitTestEvent1", pastEvent.getName());
    assertEquals("Unit Venue", pastEvent.getVenue());
    assertEquals(LocalDateTime.of(2022, 11, 11, 11, 0, 0), pastEvent.getStartTime());

    assertEquals(-67, futureEvent.getId());
    assertEquals("EventUnitTest2", futureEvent.getName());
    assertEquals("Used Unit for Testing Only", futureEvent.getDescription());


  }

  @Test
  void findAllUpcomingEventsByGroup() {
    LocalDate testDate = LocalDate.of(2028, 1,1);
    List<Event> eventList = eventMapper.findAllUpcomingEventsByGroup(testDate, -59);
    Event futureEvent = eventList.get(0);
    log.info("eventList = {}", eventList);

    assertEquals(1, eventList.size());
    assertNotEquals(2, eventList.size());
    assertEquals(-67, futureEvent.getId());
    assertEquals("Venue testing Unit", futureEvent.getVenue());
    assertEquals(LocalDateTime.of(2030, 9, 9, 9, 9, 0), futureEvent.getEndTime());
  }

  @Test
  void findUpcomingEventsByUser() {
    LocalDate testDate = LocalDate.of(2023, 1,1);
    Long userId = -69L;
    List<Event> eventList = eventMapper.findUpcomingEventsByUser(testDate, userId);
    Event futureEvent = eventList.get(0);

    log.info("eventList = {}", eventList);
    assertEquals(1, eventList.size());
    assertEquals("EventUnitTest2", futureEvent.getName());
    assertEquals(LocalDateTime.of(2030,9, 9,  9, 0, 0), futureEvent.getStartTime());
    assertEquals("Used Unit for Testing Only", futureEvent.getDescription());
  }


}
