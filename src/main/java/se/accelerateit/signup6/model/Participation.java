package se.accelerateit.signup6.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Participation {
  @JsonView(Visibility.Public.class) private Long id;

  @JsonView(Visibility.Public.class) private ParticipationStatus status = ParticipationStatus.Unregistered;
  @JsonView(Visibility.Public.class) private int numberOfParticipants = 1;
  @JsonView(Visibility.Public.class) private String comment = "";
  @JsonView(Visibility.Public.class) private Long userId;
  @JsonView(Visibility.Public.class) private Long eventId;
  @JsonView(Visibility.Public.class) private LocalDateTime signUpTime = LocalDateTime.now();

  public Participation(ParticipationStatus status, Long userId, Long eventId) {
    this.status = status;
    this.userId = userId;
    this.eventId = eventId;
  }

  public void stampTimeNow() {
    signUpTime = LocalDateTime.now();
  }
}