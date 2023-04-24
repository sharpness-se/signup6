package se.accelerateit.signup6.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import se.accelerateit.signup6.dao.GroupMapper;
import se.accelerateit.signup6.dao.MembershipMapper;
import se.accelerateit.signup6.model.Group;
import se.accelerateit.signup6.model.User;
import se.accelerateit.signup6.modelvalidator.GroupDoesNotExistException;

import java.util.List;


@RestController
public class GroupController extends BaseApiController {
  private final GroupMapper groupMapper;
  private final MembershipMapper membershipMapper;

  @Autowired
  GroupController(GroupMapper groupMapper, MembershipMapper membershipMapper) {
    this.groupMapper = groupMapper;
    this.membershipMapper = membershipMapper;
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
    return membershipMapper.findUsersByGroup(groupId);
  }


}
