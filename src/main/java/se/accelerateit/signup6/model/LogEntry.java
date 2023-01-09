package se.accelerateit.signup6.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LogEntry {
  @JsonView(Visibility.Public.class) private Long Id;
  @JsonView(Visibility.Public.class) private Long eventId;
  @JsonView(Visibility.Public.class) private String message;
  @JsonView(Visibility.Public.class) private LocalDateTime when = LocalDateTime.now();


  public LogEntry(Event event, String message) {
    this.eventId = event.getId();
    this.message = message;
  }

}
