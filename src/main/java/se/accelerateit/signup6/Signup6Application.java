package se.accelerateit.signup6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Signup6Application {

  static {
    // make enums re-usable in API documentation
    io.swagger.v3.core.jackson.ModelResolver.enumsAsRef = true;
  }

  public static void main(String[] args) {
    SpringApplication.run(Signup6Application.class, args);
  }

}
