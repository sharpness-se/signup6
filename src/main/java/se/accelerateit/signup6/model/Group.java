package se.accelerateit.signup6.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import se.accelerateit.signup6.model.Visibility.Administrator;
import se.accelerateit.signup6.model.Visibility.Public;

@Data
public class Group {
  @JsonView(Public.class) private Long id;

  @JsonView(Public.class) private String name;
  @JsonView(Public.class) private String description = "";
  @JsonView(Administrator.class) private String mailFrom = "";
  @JsonView(Administrator.class) private String mailSubjectPrefix = "";
}
