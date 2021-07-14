package se.accelerateit.signup6.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static se.accelerateit.signup6.model.EventStatus.Created;

@Data
public class Event {
  @JsonView(Visibility.Public.class) private Long id;

  @JsonView(Visibility.Public.class) private Group group;

  @JsonView(Visibility.Public.class) private String name;
  @JsonView(Visibility.Public.class) private String description = "";
  @JsonView(Visibility.Public.class) private LocalDateTime startTime;
  @JsonView(Visibility.Public.class) private LocalDateTime endTime;
  @JsonView(Visibility.Public.class) private LocalDate lastSignUpDate;
  @JsonView(Visibility.Public.class) private String venue = "";
  @JsonView(Visibility.Public.class) private boolean allowExtraFriends = false;
  @JsonView(Visibility.Public.class) private EventStatus eventStatus = Created;
  @JsonView(Visibility.Public.class) private Integer maxParticipants;
  @JsonView(Visibility.Public.class) private String cancellationReason;
}
