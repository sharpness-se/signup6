package se.accelerateit.signup6.apitest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.model.Group;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static se.accelerateit.signup6.model.EventStatus.Created;


public class EventApiTest extends SignupApiTest {

  @Test
  public void getExistingEvent() throws Exception {
    Long eventId = 99L;
    Long groupId = 9999L;

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

    Group group = new Group();
    group.setId(groupId);
    group.setName("Familjen");
    group.setDescription("Våra aktiviteter");
    group.setMailFrom("familjen@family.name");
    group.setMailSubjectPrefix("OBS!");

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
  public void getNonExistingEvent() throws Exception {
    Long userId = 88L;

    Mockito.when(userMapper.findById(userId)).thenReturn(Optional.empty());

    mockMvc.perform(get("/api/events/" + userId))
      .andExpect(status().isNotFound())
      .andExpect(content().string("Event does not exist"));
  }
}
