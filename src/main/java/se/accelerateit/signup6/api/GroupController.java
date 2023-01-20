package se.accelerateit.signup6.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import se.accelerateit.signup6.dao.GroupMapper;
import se.accelerateit.signup6.dao.MembershipMapper;
import se.accelerateit.signup6.dao.UserMapper;
import se.accelerateit.signup6.model.Group;
import se.accelerateit.signup6.model.User;
import se.accelerateit.signup6.modelvalidator.GroupDoesNotExistException;
import se.accelerateit.signup6.modelvalidator.WtfException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
public class GroupController extends BaseApiController {
  private final GroupMapper groupMapper;
  private final MembershipMapper membershipMapper;
  private final UserMapper userMapper;

  @Autowired
  GroupController(GroupMapper groupMapper, MembershipMapper membershipMapper, UserMapper userMapper) {
    this.groupMapper = groupMapper;
    this.membershipMapper = membershipMapper;
    this.userMapper = userMapper;
  }

  @GetMapping("/groups/{groupId}")
  public Group findById(@PathVariable(value = "groupId") Long groupId) {
    final var result = groupMapper.findById(groupId);
    if(result.isPresent()) {
      return result.get();
    } else {
      throw new GroupDoesNotExistException();
    }
  }

  @GetMapping("/groups/all")
  public List<Group> findAllGroups() {
    final var result = groupMapper.findAllGroups();
    if(!result.isEmpty()) {
      return result;
    } else {
      throw new WtfException();
    }
  }

  @GetMapping("/groups/findUsersByGroup/{groupId}")
  public List<Optional<User>> findUsersByGroup(@PathVariable(value = "groupId") Long groupId) {

    final var result = membershipMapper.findUsersByGroup(groupId);
    List<Optional<User>> userList = new ArrayList<>();
    if(!result.isEmpty()) {
      for (int i = 0; i < result.size(); i++) {
        userList.add(userMapper.findById(result.get(i).getUserId()));
      }
      return userList;
    } else {
      throw new GroupDoesNotExistException();
    }
  }


}
