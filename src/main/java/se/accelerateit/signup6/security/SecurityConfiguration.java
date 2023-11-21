package se.accelerateit.signup6.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final LoggedInUserService loggedInUserService;

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
            "/v1/auth/authenticate").permitAll()
          .requestMatchers(HttpMethod.POST, "/api/participations").permitAll()
          .anyRequest().authenticated();
      })
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    .authenticationProvider(authenticationProvider())
    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
  }


  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(loggedInUserService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
