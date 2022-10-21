package se.accelerateit.signup6;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class Signup6Application {

  public static void main(String[] args) {
    log.info("HERE WE GO: Starting application!!!!!!!!!!!!");
    SpringApplication.run(Signup6Application.class, args);
  }

}
