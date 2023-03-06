package se.accelerateit.signup6.integrationtest;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Container;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class SignupDbTest {
  private static final Logger logger = LoggerFactory.getLogger(SignupDbTest.class);

  @Container
  protected static final SignupDbTestcontainer dbTestContainer = SignupDbTestcontainer.getInstance();

  @Test
  @Order(0)
  void verifyThatTestDbIsRunning() {
    assertTrue(dbTestContainer.isRunning());
    logger.info("spring.datasource.url=" + dbTestContainer.getJdbcUrl());
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
