package se.accelerateit.signup6.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@Data
public class Group {
  @JsonView(Visibility.Public.class) private Long id;

  @JsonView(Visibility.Public.class) private String name;
  @JsonView(Visibility.Public.class) private String description = "";
  @JsonView(Visibility.Administrator.class) private String mailFrom = "";
  @JsonView(Visibility.Administrator.class) private String mailSubjectPrefix = "";
}
