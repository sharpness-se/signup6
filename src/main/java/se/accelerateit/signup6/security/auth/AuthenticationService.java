package se.accelerateit.signup6.security.auth;

import se.accelerateit.signup6.dao.UserMapper;
import se.accelerateit.signup6.model.Permission;
import se.accelerateit.signup6.model.SecUser;
import se.accelerateit.signup6.security.config.JwtService;
import se.accelerateit.signup6.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(RegisterRequest request) {
        User user = new User();

        user.setFirstName(request.getFirstname());
        user.setLastName(request.getLastname());
        user.setComment(request.getComment());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPermission(Permission.NormalUser);
        user.setPwd(passwordEncoder.encode(request.getPassword()));
        user.setImageProvider(request.getImageProvider());
        user.setImageVersion(request.getImageVersion());
        user.setProviderKey(request.getProviderKey());
        user.setAuthInfo(request.getAuthInfo());
// TODO: Should we use UserDetails? Because the security code chunk is based off of using this.
        var userDetails = SecUser.builder()
                .firstName(request.getFirstname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .permission(Permission.NormalUser)
                .build();

        userMapper.createUser(user);
        var jwtToken = jwtService.generateToken(userDetails);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        var user = userMapper.findByEmail(request.getEmail())
                .orElseThrow();
// TODO: Again, UserDetails.
        SecUser userDetails = new SecUser();
        userDetails.setId(user.getId());
        userDetails.setFirstName(user.getFirstName());
        userDetails.setEmail(user.getEmail());
        userDetails.setPassword(user.getPwd());
        userDetails.setPermission(user.getPermission());

        var jwtToken = jwtService.generateToken(userDetails);
        System.out.println("JWT Token bitch: " + jwtToken);
        //var builtToken = AuthenticationResponse.builder().token(jwtToken).build();
        //System.out.println(builtToken);
        return AuthenticationResponse.builder().token(jwtToken).build();

    }
}
