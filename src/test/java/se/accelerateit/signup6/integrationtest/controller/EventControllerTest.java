package se.accelerateit.signup6.integrationtest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.accelerateit.signup6.api.EventController;
import se.accelerateit.signup6.dao.EventMapper;
import se.accelerateit.signup6.dao.GroupMapper;
import se.accelerateit.signup6.integrationtest.SignupDbTest;
import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.modelvalidator.EventDoesNotExistException;
import se.accelerateit.signup6.modelvalidator.MissingParametersException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Slf4j
@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTest.Initializer.class})
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
class EventControllerTest extends SignupDbTest {
  private final EventController eventController;
  private final GroupMapper groupMapper;
  private final EventMapper eventMapper;



  @Test
  void getExistingEvent() {
    final var event = eventController.find(-2L);
    log.info("event = {}", event);

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

  @Test
  void getAllEventsByGroup() {
    final var events = eventController.findAllByGroup(-59L);
    log.info("events = {}", events);

    assertEquals(2, events.size());
    assertEquals(-67L, events.get(0).getId());
    assertEquals("EventUnitTest2", events.get(0).getName());
    assertEquals("Used Unit for Testing Only", events.get(0).getDescription());
    assertEquals(-59L, events.get(0).getGroup().getId());
    assertEquals("GroupOnlyForUnitTest", events.get(0).getGroup().getName());
    assertEquals("Gör inget med denna grupp tack", events.get(0).getGroup().getDescription());

    assertEquals(-66L, events.get(1).getId());
    assertEquals("UnitTestEvent1", events.get(1).getName());
    assertEquals("Only used for Unit Testing", events.get(1).getDescription());
    assertEquals(-59L, events.get(1).getGroup().getId());
    assertEquals("GroupOnlyForUnitTest", events.get(1).getGroup().getName());
    assertEquals("Gör inget med denna grupp tack", events.get(1).getGroup().getDescription());
  }

  @Test
  void getAllUpcomingEventsByGroup() {
    final var events = eventController.findAllUpComingEventsByGroup(-59L);
    log.info("events = {}", events);

    assertEquals(1, events.size());
    assertEquals(-67L, events.get(0).getId());
    assertEquals("EventUnitTest2", events.get(0).getName());
    assertEquals("Used Unit for Testing Only", events.get(0).getDescription());
    assertEquals(LocalDateTime.of(2030, Month.SEPTEMBER, 9, 9, 0), events.get(0).getStartTime());
    assertEquals(-59L, events.get(0).getGroup().getId());
    assertEquals("GroupOnlyForUnitTest", events.get(0).getGroup().getName());
    assertEquals("Gör inget med denna grupp tack", events.get(0).getGroup().getDescription());
  }

  @Test
  void createEvent() {
    Event event = new Event();
    event.setName("TestEvent33");
    event.setDescription("TestDescription");
    event.setStartTime(LocalDateTime.of(2030, Month.SEPTEMBER, 9, 9, 0));
    event.setEndTime(LocalDateTime.of(2030, Month.SEPTEMBER, 9, 10, 0));
    event.setLastSignUpDate(LocalDate.of(2030, Month.SEPTEMBER, 8));
    event.setGroup(groupMapper.findById(-59L).orElseThrow());

    var createdEvent = eventController.createEvent(event);

    assertEquals("TestEvent33", createdEvent.getName());
    assertEquals("TestDescription", createdEvent.getDescription());
    assertEquals(LocalDateTime.of(2030, Month.SEPTEMBER, 9, 9, 0), createdEvent.getStartTime());
    assertEquals(LocalDateTime.of(2030, Month.SEPTEMBER, 9, 10, 0), createdEvent.getEndTime());
    assertEquals(LocalDate.of(2030, Month.SEPTEMBER, 8), createdEvent.getLastSignUpDate());
    assertEquals(-59L, createdEvent.getGroup().getId());

    eventMapper.deleteEvent(createdEvent.getId());
  }

  @Test
  void createIncompleteEvent() {
    Event event = new Event();
    event.setName("TestEvent");
    event.setDescription("TestDescription");
    event.setStartTime(LocalDateTime.of(2030, Month.SEPTEMBER, 9, 9, 0));

    assertThrows(MissingParametersException.class, () -> {eventController.createEvent(event);});
  }

}
