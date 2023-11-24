package se.accelerateit.signup6.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.accelerateit.signup6.api.dto.AuthenticationRequest;
import se.accelerateit.signup6.api.dto.AuthenticationResponse;
import se.accelerateit.signup6.security.AuthenticationService;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        log.debug("In controller");
        return ResponseEntity.ok(service.authenticate(request));
    }
}
