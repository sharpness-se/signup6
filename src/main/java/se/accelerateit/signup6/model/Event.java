package se.accelerateit.signup6.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static se.accelerateit.signup6.model.EventStatus.Created;

@Data
public class Event {
  private Long id;

  private Group group;

  private String name;
  private String description = "";
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private LocalDate lastSignUpDate;
  private String venue = "";
  private boolean allowExtraFriends = false;
  private EventStatus eventStatus = Created;
  private Integer maxParticipants;
  private String cancellationReason;
}
