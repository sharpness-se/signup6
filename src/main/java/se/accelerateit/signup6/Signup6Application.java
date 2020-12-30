package se.accelerateit.signup6;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Signup6Application {

  private static Logger logger = LoggerFactory.getLogger(Signup6Application.class);

  @Value("${spring.profiles.active:}")
  private static String activeProfile;

  public static void main(String[] args) {
    SpringApplication.run(Signup6Application.class, args);
    logger.info("Spring profile being used: {}", activeProfile);
  }

}
