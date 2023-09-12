package se.accelerateit.signup6;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import se.accelerateit.signup6.security.config.JwtAuthenticationFilter;

/*

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;

  @Bean
  public SecurityFilterChain configure(HttpSecurity http) throws Exception {

    http
      .csrf(AbstractHttpConfigurer::disable) //Should this be enabled?
      .authorizeHttpRequests((authorizeHttpRequests)  -> authorizeHttpRequests
        .requestMatchers(
            "/api/swagger-ui/**"
          , "/api/v3/api-docs/**"
          , "/api/events/findAllEventsByGroupId/**"
          , "/api/events/findAllUpcomingEventsByGroupId/**"
          , "/api/events/findUpcomingEventsByUser/**"
          , "/api/events/create"
          , "/api/groups/*"
          , "/api/groups/findUsersByGroup/**"
          , "/api/groups/all"
          , "/api/participations"
          , "/api/participations/registration"
          , "/api/participations/findParticipationByEvent/**"
          , "/api/users/*"
          , "/api/reminders/trigger"
          , "/api/reminders/findAllRemindersByEventId/**"
          , "/api/logentry/findEntriesByEventId/**"
          , "/api/v1/auth/register"
          , "/api/v1/auth/**"
          , "/api/v1/auth/*"
          , "/api/v1/*"
          , "/v1/auth/**"
          , "/v1/auth/*"
          , "/v1/auth/register"
          , "/v1/auth/authenticate"
        )
        .permitAll())


    return http.build();
  }

}*/