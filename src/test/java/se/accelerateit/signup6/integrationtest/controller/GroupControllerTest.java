package se.accelerateit.signup6.integrationtest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.accelerateit.signup6.api.GroupController;
import se.accelerateit.signup6.integrationtest.SignupDbTest;
import se.accelerateit.signup6.model.Group;
import se.accelerateit.signup6.modelvalidator.GroupDoesNotExistException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Slf4j
@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTest.Initializer.class})
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
class GroupControllerTest extends SignupDbTest {
  private final GroupController groupController;



  @Test
  void getExistingGroup() {
    Group group = groupController.findById(-1L);
    assertEquals(-1L, group.getId());
    assertEquals("Crisp Rocket Days", group.getName());
    assertEquals("För dej som vill lära dej mer", group.getDescription());
    assertEquals("Crisp Rocket Days", group.getMailSubjectPrefix());
    assertEquals("", group.getMailFrom());
  }

  @Test
  void getNonExistingGroup() {
    assertThrows(GroupDoesNotExistException.class, () -> groupController.findById(3141592653589793L));
  }

  @Test
  void getAllGroups() {
    final var groups = groupController.findAllGroups();
    log.info("groups = {}", groups);
    assertEquals(4, groups.size());
  }


  @Test
  void findMembers() {
    final var users = groupController.findUsersByGroup(-1L);
    log.info("users = {}", users);
    assertEquals(2, users.size());
  }

}
