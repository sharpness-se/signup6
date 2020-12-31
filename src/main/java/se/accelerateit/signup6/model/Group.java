package se.accelerateit.signup6.model;

import lombok.Data;

@Data
public class Group {
  private Long id;

  private String name;
  private String description = "";
  private String mailFrom = "";
  private String mailSubjectPrefix = "";
}
