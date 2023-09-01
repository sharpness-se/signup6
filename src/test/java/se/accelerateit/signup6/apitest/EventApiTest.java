package se.accelerateit.signup6.apitest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import se.accelerateit.signup6.SecurityConfiguration;
import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.model.Group;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static se.accelerateit.signup6.model.EventStatus.Created;

@Import(SecurityConfiguration.class)
class EventApiTest extends SignupApiTest {

  @Test
  void getExistingEvent() throws Exception {
    Long eventId = 99L;
    Long groupId = 9999L;

    Group group = new Group();
    group.setId(groupId);
    group.setName("Familjen");
    group.setDescription("Våra aktiviteter");
    group.setMailFrom("familjen@family.name");
    group.setMailSubjectPrefix("OBS!");

    Event event = new Event();
    event.setId(eventId);
    event.setName("Julafton");
    event.setDescription("Familjefest");
    event.setStartTime(LocalDateTime.parse("2007-12-24T14:15:01"));
    event.setEndTime(LocalDateTime.parse("2007-12-24T23:00:01"));
    event.setLastSignUpDate(LocalDate.parse("2007-12-13"));
    event.setVenue("Hemma");
    event.setAllowExtraFriends(true);
    event.setEventStatus(Created);
    event.setMaxParticipants(null);
    event.setCancellationReason(null);
    event.setGroup(group);


    Mockito.when(eventMapper.findById(eventId)).thenReturn(Optional.of(event));

    var result = mockMvc.perform(get("/api/events/" + eventId)).andExpect(status().isOk());

    // TODO: verify other visibilities than Visibility.Public
    result
      .andExpect(jsonPath("$.id", Matchers.equalTo(eventId.intValue())))
      .andExpect(jsonPath("$.name", Matchers.equalTo(event.getName())))
      .andExpect(jsonPath("$.description", Matchers.equalTo(event.getDescription())))
      .andExpect(jsonPath("$.startTime", Matchers.equalTo(event.getStartTime().toString())))
      .andExpect(jsonPath("$.endTime", Matchers.equalTo(event.getEndTime().toString())))
      .andExpect(jsonPath("$.lastSignUpDate", Matchers.equalTo(event.getLastSignUpDate().toString())))
      .andExpect(jsonPath("$.venue", Matchers.equalTo(event.getVenue())))
      .andExpect(jsonPath("$.allowExtraFriends", Matchers.equalTo(event.isAllowExtraFriends())))
      .andExpect(jsonPath("$.eventStatus", Matchers.equalTo(event.getEventStatus().toString())))
      .andExpect(jsonPath("$.maxParticipants").doesNotExist())
      .andExpect(jsonPath("$.cancellationReason").doesNotExist())
      .andExpect(jsonPath("$.group.id", Matchers.equalTo(groupId.intValue())))
      .andExpect(jsonPath("$.group.name", Matchers.equalTo(group.getName())))
      .andExpect(jsonPath("$.group.description", Matchers.equalTo(group.getDescription())))
      .andExpect(jsonPath("$.group.mailFrom").doesNotExist())
      .andExpect(jsonPath("$.group.mailSubjectPrefix").doesNotExist());
  }

  @Test
  void getNonExistingEvent() throws Exception {
    Long userId = 88L;

    Mockito.when(userMapper.findById(userId)).thenReturn(Optional.empty());

    mockMvc.perform(get("/api/events/" + userId))
      .andExpect(status().isNotFound())
      .andExpect(content().string("Event does not exist"));
  }


  @Test
  void getAllEventsByGroup()  throws Exception {
    Long groupId = 99L;

    Group group = new Group();
    group.setId(groupId);
    group.setName("Familjen");
    group.setDescription("Våra aktiviteter");
    group.setMailFrom("familjen@family.name");
    group.setMailSubjectPrefix("OBS!");

    Long eventId = 99L;
    Event eventOne = new Event();
    eventOne.setId(eventId);
    eventOne.setName("eventOne");
    eventOne.setDescription("WAN");
    eventOne.setStartTime(LocalDateTime.parse("2050-12-24T14:15:01"));
    eventOne.setEndTime(LocalDateTime.parse("2050-12-24T23:00:01"));
    eventOne.setLastSignUpDate(LocalDate.parse("2050-12-13"));
    eventOne.setVenue("Hemma");
    eventOne.setAllowExtraFriends(true);
    eventOne.setEventStatus(Created);
    eventOne.setMaxParticipants(null);
    eventOne.setCancellationReason(null);
    eventOne.setGroup(group);

    Event eventTwo = new Event();
    eventTwo.setName("eventTwo");
    eventTwo.setDescription("TÅ");

    Event eventThree = new Event();
    eventThree.setName("eventThree");
    eventThree.setDescription("TRI");

    List<Event> eventList = new ArrayList<>();
    eventList.add(eventOne);
    eventList.add(eventTwo);
    eventList.add(eventThree);

    for (Event event: eventList) {
      System.out.println(event.getName());
      System.out.println(event.getDescription());
    }

    Mockito.when(eventMapper.findAllEventsByGroup(groupId))
      .thenReturn(eventList);

    var result = mockMvc.perform(get("/api/events/findAllEventsByGroupId/" + groupId))
      .andExpect(status().isOk());

    result
      .andExpect(jsonPath("$", Matchers.hasSize(3)))
      .andExpect(jsonPath("$.[1].name", Matchers.equalTo(eventTwo.getName())))
      .andExpect(jsonPath("$.[2].name", Matchers.equalTo(eventThree.getName())))
      .andExpect(jsonPath("$.[0].description", Matchers.equalTo(eventOne.getDescription())))
      .andExpect(jsonPath("$.[1].description", Matchers.equalTo(eventTwo.getDescription())))
      .andExpect(jsonPath("$.[2].description", Matchers.equalTo(eventThree.getDescription())))
      .andExpect(jsonPath("$.[2].description", Matchers.not(eventTwo.getDescription())));
  }

  @Test
  void getAllUpcomingEventsByGroup() throws Exception {
    Long eventId = 99L;
    Long groupId = 9999L;

    Group group = new Group();
    group.setId(groupId);
    group.setName("Familjen");
    group.setDescription("Våra aktiviteter");
    group.setMailFrom("familjen@family.name");
    group.setMailSubjectPrefix("OBS!");

    Event upcomingEvent = new Event();
    upcomingEvent.setId(eventId);
    upcomingEvent.setName("upcomingInnit");
    upcomingEvent.setDescription("Familjefest");
    upcomingEvent.setStartTime(LocalDateTime.parse("2050-12-24T14:15:01"));
    upcomingEvent.setEndTime(LocalDateTime.parse("2050-12-24T23:00:01"));
    upcomingEvent.setLastSignUpDate(LocalDate.parse("2050-12-13"));
    upcomingEvent.setVenue("Hemma");
    upcomingEvent.setAllowExtraFriends(true);
    upcomingEvent.setEventStatus(Created);
    upcomingEvent.setMaxParticipants(null);
    upcomingEvent.setCancellationReason(null);
    upcomingEvent.setGroup(group);

    Event upcomingEvent2 = new Event();
    upcomingEvent2.setStartTime(LocalDateTime.parse("2045-12-24T14:15:01"));
    upcomingEvent2.setEndTime(LocalDateTime.parse("2045-12-24T23:00:01"));

    List<Event> upcomingEventList = new ArrayList<>();
    upcomingEventList.add(upcomingEvent);
    upcomingEventList.add(upcomingEvent2);

    Mockito.when(eventMapper.findAllUpcomingEventsByGroup(
      LocalDate.now(), groupId)).thenReturn(upcomingEventList);

    var result = mockMvc.perform(get("/api/events/findAllUpcomingEventsByGroupId/" + groupId))
        .andExpect(status().isOk());

    result
      .andExpect(jsonPath("$", Matchers.hasSize(2)))
      .andExpect(jsonPath("$.[0].id", Matchers.equalTo(eventId.intValue())))
      .andExpect(jsonPath("$.[0].name", Matchers.equalTo(upcomingEvent.getName())))
      .andExpect(jsonPath("$.[0].allowExtraFriends", Matchers.equalTo(upcomingEvent.isAllowExtraFriends())))
      .andExpect(jsonPath("$.[0].startTime", Matchers.equalTo(upcomingEvent.getStartTime().toString())))
      .andExpect(jsonPath("$.[1].startTime", Matchers.equalTo(upcomingEvent2.getStartTime().toString())))
      .andExpect(jsonPath("$.[0].endTime", Matchers.equalTo(upcomingEvent.getEndTime().toString())))
      .andExpect(jsonPath("$.[1].endTime", Matchers.equalTo(upcomingEvent2.getEndTime().toString())));
  }

  @Test
  void createEvent() throws Exception {

    Group group = new Group();
    group.setId(-50L);
    group.setName("Group 50");
    group.setDescription("Beskrivning");
    group.setMailFrom("email@email.com");
    group.setMailSubjectPrefix("Prefix!");

    Event testEvent = new Event();
    testEvent.setId(-50L);
    testEvent.setName("Create Event Test Name");
    testEvent.setDescription("Event beskrivning");
    testEvent.setStartTime(LocalDateTime.parse("2050-12-24T14:15:01"));
    testEvent.setEndTime(LocalDateTime.parse("2050-12-24T23:00:01"));
    testEvent.setLastSignUpDate(LocalDate.parse("2050-12-13"));
    testEvent.setVenue("Adress 1");
    testEvent.setAllowExtraFriends(true);
    testEvent.setEventStatus(Created);
    testEvent.setMaxParticipants(null);
    testEvent.setCancellationReason(null);
    testEvent.setGroup(group);

    mockMvc.perform(post("/api/events/create")
        .content(jsonMapper.writeValueAsString(testEvent))
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id", Matchers.equalTo(testEvent.getId().intValue())))
      .andExpect(jsonPath("$.name", Matchers.equalTo(testEvent.getName())))
      .andExpect(jsonPath("$.description", Matchers.equalTo(testEvent.getDescription())))
      .andExpect(jsonPath("$.startTime", Matchers.equalTo("2050-12-24T14:15:01")))
      .andExpect(jsonPath("$.endTime", Matchers.equalTo("2050-12-24T23:00:01")))
      .andExpect(jsonPath("$.lastSignUpDate", Matchers.equalTo("2050-12-13")))
      .andExpect(jsonPath("$.venue", Matchers.equalTo(testEvent.getVenue())))
      .andExpect(jsonPath("$.allowExtraFriends", Matchers.equalTo(testEvent.isAllowExtraFriends())))
      .andExpect(jsonPath("$.eventStatus", Matchers.equalTo("Created")))
      .andExpect(jsonPath("$.maxParticipants", Matchers.equalTo(testEvent.getMaxParticipants())))
      .andExpect(jsonPath("$.cancellationReason", Matchers.equalTo(testEvent.getCancellationReason())))
      .andExpect(jsonPath("$.group.id", Matchers.equalTo(testEvent.getGroup().getId().intValue())));

    Mockito.verify(eventMapper, Mockito.times(1)).createEvent(testEvent);

  }
}
