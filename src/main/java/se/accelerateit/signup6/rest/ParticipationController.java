package se.accelerateit.signup6.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.accelerateit.signup6.dao.ParticipationMapper;
import se.accelerateit.signup6.model.Participation;
import se.accelerateit.signup6.model.ParticipationStatus;
import se.accelerateit.signup6.modelvalidator.EventValidator;
import se.accelerateit.signup6.modelvalidator.NotMemberOfGroupException;

import java.util.Optional;

@RestController
public class ParticipationController {
  private final ParticipationMapper participationMapper;
  private final EventValidator eventValidator;

  ParticipationController(ParticipationMapper participationMapper, EventValidator eventValidator) {
    this.participationMapper = participationMapper;
    this.eventValidator = eventValidator;
  }


  @GetMapping("/participations")
  Participation findOrCreate(@RequestParam(value = "userId") Long userId,
                             @RequestParam(value = "eventId") Long eventId) {
    Optional<Participation> result = participationMapper.findByUserAndEvent(userId, eventId);
    if(result.isPresent()) {
      return result.get();
    } else {
      if(eventValidator.isMemberOfGroupForEvent(userId, eventId)) {
        return new Participation(ParticipationStatus.Unregistered, userId, eventId);
      } else {
        throw new NotMemberOfGroupException();
      }
    }
  }

}
