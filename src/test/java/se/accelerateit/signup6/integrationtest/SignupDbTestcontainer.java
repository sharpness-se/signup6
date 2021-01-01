package se.accelerateit.signup6.integrationtest;


import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
public class SignupDbTestcontainer extends PostgreSQLContainer<SignupDbTestcontainer> {
  private static final String IMAGE_VERSION = "postgres:latest";
  private static SignupDbTestcontainer container;

  private SignupDbTestcontainer() {
    super(IMAGE_VERSION);
  }

  public static SignupDbTestcontainer getInstance() {
    if (container == null) {
      container = new SignupDbTestcontainer()
        .withDatabaseName("signup")
        .withUsername("signup4")
        .withPassword("password");
    }
    return container;
  }

  @Override
  public void stop() {
  }
}