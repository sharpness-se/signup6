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
  void checkMembership() {

    assertEquals(-1L, groupMapper.findMembershipForEvent(-1L, -2L).orElseThrow(), "User -1 should be member of group where event -2 is planned");
    assertEquals(-2L, groupMapper.findMembershipForEvent(-2L, -2L).orElseThrow(), "User -2 should be member of group where event -2 is planned");
    assertEquals(-3L, groupMapper.findMembershipForEvent(-3L, -3L).orElseThrow(), "User -3 should be member of group where event -3 is planned");

    assertFalse(groupMapper.findMembershipForEvent(-5L, -2L).isPresent(), "User -5 should NOT be member of group where event -2 is planned");
    assertFalse(groupMapper.findMembershipForEvent(-1L, -222L).isPresent(), "User -1 should NOT be member of group for a non-existent event -222");
    assertFalse(groupMapper.findMembershipForEvent(-111L, -2L).isPresent(), "Non-existing user -111 NOT be member of group where event -2 is planned");
    assertFalse(groupMapper.findMembershipForEvent(-111L, -222L).isPresent(), "Non-existing user -111 NOT be member of group for a non-existent event -222");

  }

  @Test
  void insertMonsterGroupTest(){
    Group monsterGroup = new Group();
    monsterGroup.setId(-99L);
    monsterGroup.setName("Monster Consumers");
    monsterGroup.setDescription("Excessive amounts of caffeine consumption");
    monsterGroup.setMailFrom("monstergroup@monster.com");
    monsterGroup.setMailSubjectPrefix("CONSUME!!");

    try{
      groupMapper.insertGroup(monsterGroup);
    }catch (Exception e){
      e.printStackTrace();
    }
    
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
  void findAllGroupsTest(){
    List<Group> groupList = groupMapper.findAllGroups();
    assertFalse(groupList.isEmpty());
    assertEquals(2, groupList.size());

  }
}
