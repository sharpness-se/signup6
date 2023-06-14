package se.accelerateit.signup6.apitest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import se.accelerateit.signup6.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



public class ParticipationApiTest extends SignupApiTest {



  @Test
  public void getExistingParticipation() throws Exception {
    final Long id = 99L;
    final Long userId = -1L;
    final Long eventId = -3L;

    Participation participation = new Participation(ParticipationStatus.On, userId, eventId);
    participation.setId(id);

    Mockito.when(participationMapper.findByUserAndEvent(userId, eventId)).thenReturn(Optional.of(participation));
    Mockito.when(eventValidator.isMemberOfGroupForEvent(userId, eventId)).thenReturn(true);

    mockMvc.perform(get("/api/participations").param("userId", userId.toString()).param("eventId", eventId.toString()))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id", Matchers.equalTo(id.intValue())))
      .andExpect(jsonPath("$.status", Matchers.equalTo(ParticipationStatus.On.toString())))
      .andExpect(jsonPath("$.numberOfParticipants", Matchers.equalTo(1)))
      .andExpect(jsonPath("$.userId", Matchers.equalTo(userId.intValue())))
      .andExpect(jsonPath("$.eventId", Matchers.equalTo(eventId.intValue())));
  }
  @Test
  public void findParticipationStatusByEvent() throws Exception {
    final Long eventId = -1L;

    Event event = new Event();
    event.setId(-1L);
    Group group = new Group();
    group.setId(-1L);
    event.setGroup(group);
    Mockito.when(eventMapper.findById(eventId)).thenReturn(Optional.of(event));

    List<User> users = new ArrayList<>();
    User user = new User();
    users.add(user);
    List<User> emptyUsers = new ArrayList<>();
    Mockito.when(userMapper.findUnregisteredMembers(eventId)).thenReturn(users);
    Mockito.when(userMapper.findMembersByStatus(ParticipationStatus.On, event)).thenReturn(users);
    Mockito.when(userMapper.findMembersByStatus(ParticipationStatus.Maybe, event)).thenReturn(emptyUsers);
    Mockito.when(userMapper.findMembersByStatus(ParticipationStatus.Off, event)).thenReturn(emptyUsers);

    mockMvc.perform(get("/api/participations/findParticipationByEvent/" + eventId))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.on.length()", Matchers.equalTo(1)))
      .andExpect(jsonPath("$.maybe.length()", Matchers.equalTo(0)))
      .andExpect(jsonPath("$.off.length()", Matchers.equalTo(0)))
      .andExpect(jsonPath("$.unregistered.length()", Matchers.equalTo(1)));

  }
  @Test
  public void getNonExistingParticipation() throws Exception {
    final Long userId = -1L;
    final Long eventId = -3L;

    Mockito.when(participationMapper.findByUserAndEvent(userId, eventId)).thenReturn(Optional.empty());
    Mockito.when(eventValidator.isMemberOfGroupForEvent(userId, eventId)).thenReturn(true);

    mockMvc.perform(get("/api/participations").param("userId", userId.toString()).param("eventId", eventId.toString()))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id", Matchers.equalTo(null)))
      .andExpect(jsonPath("$.status", Matchers.equalTo(ParticipationStatus.Unregistered.toString())))
      .andExpect(jsonPath("$.numberOfParticipants", Matchers.equalTo(1)))
      .andExpect(jsonPath("$.userId", Matchers.equalTo(userId.intValue())))
      .andExpect(jsonPath("$.eventId", Matchers.equalTo(eventId.intValue())));
  }

  @Test
  public void getInvalidParticipation() throws Exception {
    final Long userId = -11L;
    final Long eventId = -333L;

    Mockito.when(participationMapper.findByUserAndEvent(userId, eventId)).thenReturn(Optional.empty());
    Mockito.when(eventValidator.isMemberOfGroupForEvent(userId, eventId)).thenReturn(false);

    mockMvc.perform(get("/api/participations").param("userId", userId.toString()).param("eventId", eventId.toString()))
      .andExpect(status().isNotFound())
      .andExpect(content().string("User is not member of required group"));
  }

  @Test
  public void updateExistingParticipation() throws Exception {
    final Long id = 99L;
    final Long userId = -1L;
    final Long eventId = -3L;

    Participation participation = new Participation(ParticipationStatus.On, userId, eventId);
    participation.setId(id);

    Mockito.when(participationMapper.findByUserAndEvent(userId, eventId)).thenReturn(Optional.of(participation));
    Mockito.when(participationMapper.update(participation)).thenReturn(1);


    mockMvc.perform(post("/api/participations")
      .content(jsonMapper.writeValueAsString(participation))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id", Matchers.equalTo(id.intValue())))
      .andExpect(jsonPath("$.status", Matchers.equalTo(ParticipationStatus.On.toString())))
      .andExpect(jsonPath("$.numberOfParticipants", Matchers.equalTo(1)))
      .andExpect(jsonPath("$.userId", Matchers.equalTo(userId.intValue())))
      .andExpect(jsonPath("$.eventId", Matchers.equalTo(eventId.intValue())));

    Mockito.verify(participationMapper, Mockito.times(1)).update(participation);
  }

  @Test
  public void createNewParticipation() throws Exception {
    final Long id = 99L;
    final Long userId = -1L;
    final Long eventId = -3L;

    Participation participation = new Participation(ParticipationStatus.On, userId, eventId);
    participation.setId(id);

    Mockito.when(participationMapper.findByUserAndEvent(userId, eventId)).thenReturn(Optional.empty()).thenReturn(Optional.of(participation));
    Mockito.when(eventValidator.isMemberOfGroupForEvent(userId, eventId)).thenReturn(true);

    mockMvc.perform(post("/api/participations")
      .content(jsonMapper.writeValueAsString(participation))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id", Matchers.equalTo(id.intValue())))
      .andExpect(jsonPath("$.status", Matchers.equalTo(ParticipationStatus.On.toString())))
      .andExpect(jsonPath("$.numberOfParticipants", Matchers.equalTo(1)))
      .andExpect(jsonPath("$.userId", Matchers.equalTo(userId.intValue())))
      .andExpect(jsonPath("$.eventId", Matchers.equalTo(eventId.intValue())));

    Mockito.verify(participationMapper, Mockito.never()).update(participation);
    Mockito.verify(participationMapper, Mockito.times(1)).create(participation);
  }

  @Test
  public void updateInvalidParticipation() throws Exception {
    final Long id = 999L;
    final Long userId = -11L;
    final Long eventId = -333L;

    Participation participation = new Participation(ParticipationStatus.On, userId, eventId);
    participation.setId(id);

    Mockito.when(participationMapper.findByUserAndEvent(userId, eventId)).thenReturn(Optional.empty());
    Mockito.when(eventValidator.isMemberOfGroupForEvent(userId, eventId)).thenReturn(false);


    mockMvc.perform(post("/api/participations")
      .content(jsonMapper.writeValueAsString(participation))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound())
      .andExpect(content().string("User is not member of required group"));

    Mockito.verify(participationMapper, Mockito.never()).update(participation);
    Mockito.verify(participationMapper, Mockito.never()).create(participation);
  }

  @Test
  public void internalErrorTest() throws Exception {
    final Long id = 99L;
    final Long userId = -1L;
    final Long eventId = -3L;

    Participation participation = new Participation(ParticipationStatus.On, userId, eventId);
    participation.setId(id);

    Mockito.when(participationMapper.findByUserAndEvent(userId, eventId)).thenReturn(Optional.empty());
    Mockito.when(eventValidator.isMemberOfGroupForEvent(userId, eventId)).thenReturn(true);

    mockMvc.perform(post("/api/participations")
      .content(jsonMapper.writeValueAsString(participation))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isInternalServerError())
      .andExpect(content().string("This doesn't make sense. Don't know what's going on."));
  }

}
