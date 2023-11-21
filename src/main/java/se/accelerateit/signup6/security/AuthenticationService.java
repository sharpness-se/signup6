package se.accelerateit.signup6.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.accelerateit.signup6.api.dto.AuthenticationRequest;
import se.accelerateit.signup6.api.dto.AuthenticationResponse;
import se.accelerateit.signup6.dao.UserMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


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
        LoggedInUser loggedInUser = new LoggedInUser(user);

        var jwtToken = jwtService.generateToken(loggedInUser);
        log.debug("JWT Token bitch: " + jwtToken);
        //var builtToken = AuthenticationResponse.builder().token(jwtToken).build();
        //System.out.println(builtToken);
        return AuthenticationResponse.builder().token(jwtToken).build();

    }
}
