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

import java.util.ArrayList;
import java.util.List;


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
    return groupMapper.findAllGroups();
  }

  @GetMapping("/groups/findUsersByGroup/{groupId}")
  public List<User> findUsersByGroup(@PathVariable(value = "groupId") Long groupId) {
    final var result = membershipMapper.findUsersByGroup(groupId);
    if(!result.isEmpty()) {
      List<User> userList = new ArrayList<>();
      // TODO: replace with SQL query to get all user objects instead of just the ids
      for (se.accelerateit.signup6.model.Membership membership : result) {
        //noinspection OptionalGetWithoutIsPresent
        User user = userMapper.findById(membership.getUserId()).get();
        user.setImageVersion(UserController.adjustedImageVersion(user));
        userList.add(user);
      }
      return userList;
    } else {
      // TODO: return empty list instead of throwing exception
      throw new GroupDoesNotExistException();
    }
  }


}
