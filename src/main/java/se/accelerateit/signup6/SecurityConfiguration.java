package se.accelerateit.signup6;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Bean
  public SecurityFilterChain configure(HttpSecurity http) throws Exception {

    http
      .csrf(csrf -> csrf.disable()) //Should this be enabled?
      .authorizeHttpRequests((authorizeHttpRequests)  -> authorizeHttpRequests
        .requestMatchers(
            "/api/swagger-ui/**"
          , "/api/v3/api-docs/**"
          , "/api/events/*"
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
        )

        .permitAll());

    return http.build();
  }

}