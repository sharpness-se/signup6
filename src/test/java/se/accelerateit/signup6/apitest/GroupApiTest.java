package se.accelerateit.signup6.apitest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import se.accelerateit.signup6.model.Group;
import se.accelerateit.signup6.model.Membership;
import se.accelerateit.signup6.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class GroupApiTest extends SignupApiTest{

  @Test
  void findGroupById() throws Exception {
    Long groupId = 9999L;

    Group group = new Group();
    group.setId(groupId);
    group.setName("Sharpness");
    group.setDescription("Sharpness time");
    group.setMailFrom("Sharpness@Sharpness.Sharpness");
    group.setMailSubjectPrefix("Sharpness!");

    Mockito.when(groupMapper.findById(groupId))
      .thenReturn(Optional.of(group));

    var result = mockMvc.perform(get("/api/groups/" + groupId))
      .andExpect(status().isOk());


    result
      .andExpect(jsonPath("$.id", Matchers.equalTo(groupId.intValue())))
      .andExpect(jsonPath("$.name", Matchers.equalTo(group.getName())))
      .andExpect(jsonPath("$.description", Matchers.equalTo(group.getDescription())));
  }


  @Test
  void findAllGroups() throws Exception {
    Long groupId = 9999L;

    Group groupOne = new Group();
    groupOne.setId(groupId);
    groupOne.setName("Sharpness");
    groupOne.setDescription("Sharpness time");

    Group groupTwo = new Group();
    groupTwo.setId(groupId);
    groupTwo.setName("Bepsi");
    groupTwo.setDescription("BEPSEI TAJMM");

    Group groupThree = new Group();
    groupThree.setId(groupId);
    groupThree.setName("Pyttipanna");
    groupThree.setDescription("pytt");

    List<Group> groupList = new ArrayList<>();
    groupList.add(groupOne);
    groupList.add(groupTwo);
    groupList.add(groupThree);

    Mockito.when(groupMapper.findAllGroups())
      .thenReturn(groupList);

    var result = mockMvc.perform(get("/api/groups/all"))
      .andExpect(status().isOk());

    result
      .andExpect(jsonPath("$", Matchers.hasSize(3)))
      .andExpect(jsonPath("$.[0].name", Matchers.equalTo(groupOne.getName())))
      .andExpect(jsonPath("$.[1].name", Matchers.equalTo(groupTwo.getName())))
      .andExpect(jsonPath("$.[2].name", Matchers.equalTo("Pyttipanna")))
      .andExpect(jsonPath("$.[0].description", Matchers.equalTo(groupOne.getDescription())))
      .andExpect(jsonPath("$.[1].description", Matchers.equalTo("BEPSEI TAJMM")))
      .andExpect(jsonPath("$.[2].description", Matchers.equalTo(groupThree.getDescription())));
  }


  @Test
  void findUserByGroup() throws Exception {
    Long groupId = 9999L;

    Group group = new Group();
    group.setId(groupId);
    group.setName("Sharpness");
    group.setDescription("Sharpness time");

    User userOne = new User();
    userOne.setId(99L);
    userOne.setFirstName("Goblin");
    userOne.setLastName("Bobblin");
    userOne.setEmail("gob@gob.gob");

    User userTwo = new User();
    userTwo.setId(999L);
    userTwo.setFirstName("Vatten");
    userTwo.setLastName("Flaska");
    userTwo.setEmail("vatten@flaska.vattenflaska");

    Membership memberOne = new Membership();
    memberOne.setId(99L);
    memberOne.setGroupId(groupId);
    memberOne.setUserId(userOne.getId());

    Membership memberTwo = new Membership();
    memberTwo.setId(999L);
    memberTwo.setGroupId(groupId);
    memberTwo.setUserId(userTwo.getId());

    List<User> memberList = new ArrayList<>();
    memberList.add(userOne);
    memberList.add(userTwo);

    Mockito.when(membershipMapper.findUsersByGroup(groupId))
      .thenReturn(memberList);

    Mockito.when(userMapper.findById(userOne.getId()))
      .thenReturn(Optional.of(userOne));
    Mockito.when(userMapper.findById(userTwo.getId()))
      .thenReturn(Optional.of(userTwo));


    var result = mockMvc.perform(get("/api/groups/findUsersByGroup/" + groupId))
      .andExpect(status().isOk());

    result
      .andExpect(jsonPath("$", Matchers.hasSize(2)))
      .andExpect(jsonPath("$.[0].firstName", Matchers.equalTo(userOne.getFirstName())))
      .andExpect(jsonPath("$.[0].lastName", Matchers.equalTo(userOne.getLastName())))
      .andExpect(jsonPath("$.[0].id", Matchers.equalTo(userOne.getId().intValue())))
      .andExpect(jsonPath("$.[1].firstName", Matchers.equalTo(userTwo.getFirstName())))
      .andExpect(jsonPath("$.[1].lastName", Matchers.equalTo(userTwo.getLastName())))
      .andExpect(jsonPath("$.[1].id", Matchers.equalTo(userTwo.getId().intValue())));
  }

}
