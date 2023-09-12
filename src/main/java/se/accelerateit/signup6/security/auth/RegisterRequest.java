package se.accelerateit.signup6.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.accelerateit.signup6.model.ImageProvider;
import se.accelerateit.signup6.model.Permission;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstname;
    private String lastname;
    private String comment;
    private String email;
    private String phone;
    private Permission permission;
    private String password;
    private ImageProvider imageProvider;
    private String imageVersion;
    private String providerKey;
    private String authInfo;
}
