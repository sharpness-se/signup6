package se.accelerateit.signup6.integrationtest.dao;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.accelerateit.signup6.dao.GroupMapper;
import se.accelerateit.signup6.integrationtest.SignupDbTest;
import se.accelerateit.signup6.model.Group;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTest.Initializer.class})
class GroupMapperTest extends SignupDbTest {
  static final Logger logger = LoggerFactory.getLogger(GroupMapperTest.class);

  private final GroupMapper groupMapper;

  @Autowired
  GroupMapperTest(GroupMapper groupMapper) {
    this.groupMapper = groupMapper;
  }


  @Test
  void findGroupById() {
    Optional<Group> dbResponse = groupMapper.findById(-1L);
    assertTrue(dbResponse.isPresent(), "could not find the group in db");
    Group group = dbResponse.get();
    logger.info("group = {}", group);

    assertEquals("Crisp Rocket Days", group.getName());
    assertEquals("För dej som vill lära dej mer", group.getDescription());
    assertEquals("", group.getMailFrom());
    assertEquals("Crisp Rocket Days", group.getMailSubjectPrefix());
  }

  @Test
  void insertMonsterGroupTest() {
    Group monsterGroup = new Group();
    monsterGroup.setId(-99L);
    monsterGroup.setName("Monster Consumers");
    monsterGroup.setDescription("Excessive amounts of caffeine consumption");
    monsterGroup.setMailFrom("monstergroup@monster.com");
    monsterGroup.setMailSubjectPrefix("CONSUME!!");

    groupMapper.createGroup(monsterGroup);

    Optional<Group> dbResponse = groupMapper.findById(1L);
    assertTrue(dbResponse.isPresent(), "Could not find specified group");
    Group group = dbResponse.get();
    logger.info("group = {}", group);

    assertEquals("Monster Consumers", group.getName());
    assertEquals("Excessive amounts of caffeine consumption", group.getDescription());
    assertEquals("monstergroup@monster.com", group.getMailFrom());
    assertEquals("CONSUME!!", group.getMailSubjectPrefix());

  }

  @Test
  void findAllGroupsTest() {
    List<Group> groupList = groupMapper.findAllGroups();
    assertFalse(groupList.isEmpty());
    assertEquals(4, groupList.size());

  }
}
