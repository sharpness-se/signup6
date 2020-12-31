package se.accelerateit.signup6.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Participation {
  private Long id;

  private ParticipationStatus status = ParticipationStatus.On;
  private int numberOfParticipants = 1;
  private String comment = "";
  private User user;
  private Event event;
  private LocalDateTime signUpTime;
}