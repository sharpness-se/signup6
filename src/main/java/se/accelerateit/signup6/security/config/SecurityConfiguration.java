package se.accelerateit.signup6.security.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  //private final JwtAuthenticationFilter jwtAuthFilter;
  //private final AuthenticationProvider authenticationProvider;

  @Bean
  public SecurityFilterChain requestMatchers(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.
      csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(auth -> {
        auth.requestMatchers(HttpMethod.GET,
            "/api/v1/auth/**",
            "/api/swagger-ui/**",
            "/api/v3/api-docs/**",
            "/api/events/findAllEventsByGroupId/**",
            "/api/events/findAllUpcomingEventsByGroupId/**",
            "/api/events/findUpcomingEventsByUser/**",
            "/api/events/*",
            "/api/groups/*",
            "/api/groups/findUsersByGroup/**",
            "/api/groups/all",
            "/api/participations",
            "/api/participations/registration",
            "/api/participations/findParticipationByEvent/**",
            "/api/users/*",
            "/api/reminders/trigger",
            "/api/reminders/findAllRemindersByEventId/**",
            "/api/logentry/findEntriesByEventId/**",
            "/api/v1/auth/register",
            "/api/v1/auth/**",
            "/api/v1/auth/*",
            "/api/v1/*",
            "/v1/auth/**",
            "/v1/auth/*",
            "/v1/auth/register",
            "/v1/auth/authenticate")
          .permitAll()
          .requestMatchers(HttpMethod.POST, "/api/participations")
          .permitAll()
          .anyRequest()
          .authenticated();
      })
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //.authenticationProvider(authenticationProvider)
    //.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    // This is how 3.1* works instead of below
                /*.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);*/
    ;
    return httpSecurity.build();
  }
}
