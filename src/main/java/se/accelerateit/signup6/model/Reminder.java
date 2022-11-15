package se.accelerateit.signup6.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.accelerateit.signup6.model.Visibility.Public;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Reminder {

  @JsonView(Public.class) private Long id;
  @JsonView(Public.class) private Long eventId;
  @JsonView(Public.class) private LocalDate dateToRemind;

}
