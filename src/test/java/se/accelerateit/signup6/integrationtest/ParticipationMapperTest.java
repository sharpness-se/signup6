package se.accelerateit.signup6.integrationtest;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.accelerateit.signup6.dao.ParticipationMapper;
import se.accelerateit.signup6.model.Participation;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;


@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTest.Initializer.class})
class ParticipationMapperTest extends SignupDbTest {
  Logger logger = LoggerFactory.getLogger(ParticipationMapperTest.class);

  private final ParticipationMapper participationMapper;

  @Autowired
  ParticipationMapperTest(ParticipationMapper participationMapper) {
    this.participationMapper = participationMapper;
  }


  @Test
  void findOneParticipation() {
    Optional<Participation> dbResponse = participationMapper.findByUserAndEvent(-4L, -1L);
    assertTrue(dbResponse.isPresent(), "could not find the participation in db");
    Participation participation = dbResponse.get();
    logger.info("participation = {}", participation);
  }

  // search for non-existing participation

  // update participation

  // create participation
}
