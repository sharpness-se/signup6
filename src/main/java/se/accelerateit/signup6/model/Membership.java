package se.accelerateit.signup6.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

// Janne: everybody needs to have the same formatting settings
@Data
public class Membership {
    @JsonView(Visibility.Public.class) private Long Id;

    @JsonView(Visibility.Public.class) private Long userId;
    @JsonView(Visibility.Public.class) private Long groupId;
}
