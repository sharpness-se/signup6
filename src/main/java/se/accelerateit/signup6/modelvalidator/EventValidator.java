package se.accelerateit.signup6.modelvalidator;

import org.springframework.stereotype.Service;
import se.accelerateit.signup6.dao.GroupMapper;

import java.util.Optional;

@Service
public class EventValidator {
  private final GroupMapper groupMapper;

  public EventValidator(GroupMapper groupMapper) {
    this.groupMapper = groupMapper;
  }

  public boolean isMemberOfGroupForEvent(Long userId, Long eventId) {
    Optional<Long> result = groupMapper.findMembershipForEvent(userId, eventId);
    return result.isPresent();
  }
}
