package se.accelerateit.signup6.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import se.accelerateit.signup6.model.Visibility.Public;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static se.accelerateit.signup6.model.EventStatus.Created;


@Data
public class Event {
  @JsonView(Public.class) private Long id;

  @JsonView(Public.class) private Group group;

  @JsonView(Public.class) private String name;
  @JsonView(Public.class) private String description = "";
  @JsonView(Public.class) private LocalDateTime startTime;
  @JsonView(Public.class) private LocalDateTime endTime;
  @JsonView(Public.class) private LocalDate lastSignUpDate;
  @JsonView(Public.class) private String venue = "";
  @JsonView(Public.class) private boolean allowExtraFriends = false;
  @JsonView(Public.class) private EventStatus eventStatus = Created;
  @JsonView(Public.class) private Integer maxParticipants;
  @JsonView(Public.class) private String cancellationReason;
}
