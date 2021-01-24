package se.accelerateit.signup6.apitest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import se.accelerateit.signup6.dao.ParticipationMapper;
import se.accelerateit.signup6.model.Participation;
import se.accelerateit.signup6.model.ParticipationStatus;
import se.accelerateit.signup6.modelvalidator.EventValidator;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ParticipationTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private EventValidator eventValidator;

  @MockBean
  private ParticipationMapper participationMapper;


  private static ObjectMapper jsonMapper = new ObjectMapper();

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
}
