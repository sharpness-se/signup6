package se.accelerateit.signup6.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Participation {
  private Long id;

  private ParticipationStatus status = ParticipationStatus.Unregistered;
  private int numberOfParticipants = 1;
  private String comment = "";
  private Long userId;
  private Long eventId;
  private LocalDateTime signUpTime = LocalDateTime.now();

  public Participation(ParticipationStatus status, Long userId, Long eventId) {
    this.status = status;
    this.userId = userId;
    this.eventId = eventId;
  }

  public void stampTimeNow() {
    signUpTime = LocalDateTime.now();
  }
}