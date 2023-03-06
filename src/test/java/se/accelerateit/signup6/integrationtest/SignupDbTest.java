package se.accelerateit.signup6.integrationtest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Container;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class SignupDbTest {
  @Container
  protected static final SignupDbTestcontainer dbTestContainer = SignupDbTestcontainer.getInstance();

  @Test
  @Order(0)
  void verifyThatTestDbIsRunning() {
    assertTrue(dbTestContainer.isRunning());
    log.info("spring.datasource.url=" + dbTestContainer.getJdbcUrl());
  }

  public static class Initializer
    implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      TestPropertyValues.of(
        "spring.datasource.url=" + dbTestContainer.getJdbcUrl(),
        "spring.datasource.username=" + dbTestContainer.getUsername(),
        "spring.datasource.password=" + dbTestContainer.getPassword()
      ).applyTo(configurableApplicationContext.getEnvironment());
    }
  }
}
