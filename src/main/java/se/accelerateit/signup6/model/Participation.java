package se.accelerateit.signup6.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.accelerateit.signup6.model.Visibility.Public;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Participation {
  @JsonView(Public.class) private Long id;

  @JsonView(Public.class) private ParticipationStatus status = ParticipationStatus.Unregistered;
  @JsonView(Public.class) private int numberOfParticipants = 1;
  @JsonView(Public.class) private String comment = "";
  @JsonView(Public.class) private Long userId;
  @JsonView(Public.class) private Long eventId;
  @JsonView(Public.class) private LocalDateTime signUpTime = LocalDateTime.now();

  public Participation(ParticipationStatus status, Long userId, Long eventId) {
    this.status = status;
    this.userId = userId;
    this.eventId = eventId;
  }

  public void stampTimeNow() {
    signUpTime = LocalDateTime.now();
  }
}