package se.accelerateit.signup6.modelvalidator;

import org.springframework.stereotype.Service;
import se.accelerateit.signup6.dao.MembershipMapper;

import java.util.Optional;

@Service
public class EventValidator {
  private final MembershipMapper membershipMapper;

  public EventValidator(MembershipMapper membershipMapper) {
    this.membershipMapper = membershipMapper;
  }

  public boolean isMemberOfGroupForEvent(Long userId, Long eventId) {
    Optional<Long> result = membershipMapper.findMembershipForEvent(userId, eventId);
    return result.isPresent();
  }
}
