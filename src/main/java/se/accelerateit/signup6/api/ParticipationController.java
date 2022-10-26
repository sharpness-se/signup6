package se.accelerateit.signup6.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.accelerateit.signup6.dao.ParticipationMapper;
import se.accelerateit.signup6.model.Participation;
import se.accelerateit.signup6.model.ParticipationStatus;
import se.accelerateit.signup6.modelvalidator.EventValidator;
import se.accelerateit.signup6.modelvalidator.NotMemberOfGroupException;
import se.accelerateit.signup6.modelvalidator.WtfException;

import java.util.Optional;

@RestController
public class ParticipationController extends BaseApiController {
  private final ParticipationMapper participationMapper;
  private final EventValidator eventValidator;

  @Autowired
  ParticipationController(ParticipationMapper participationMapper, EventValidator eventValidator) {
    this.participationMapper = participationMapper;
    this.eventValidator = eventValidator;
  }


  @GetMapping("/participations")
  public Participation find(@RequestParam(value = "userId") Long userId,
                            @RequestParam(value = "eventId") Long eventId) {
    final var result = participationMapper.findByUserAndEvent(userId, eventId);
    if(result.isPresent()) {
      return result.get();
    } else if(eventValidator.isMemberOfGroupForEvent(userId, eventId)) {
        return new Participation(ParticipationStatus.Unregistered, userId, eventId);
    } else {
        throw new NotMemberOfGroupException();
    }
  }

  @PostMapping("/participations")
  public Participation updateOrCreate(@RequestBody Participation participation) {
    final var p = participationMapper.findByUserAndEvent(participation.getUserId(), participation.getEventId());
    if(p.isPresent()) {
      final var oldParticipation = p.get();
      participation.setId(oldParticipation.getId());
      participationMapper.update(participation);
    } else if(eventValidator.isMemberOfGroupForEvent(participation.getUserId(), participation.getEventId())){
      participationMapper.create(participation);
    } else {
      throw new NotMemberOfGroupException();
    }
    return participationMapper.findByUserAndEvent(participation.getUserId(), participation.getEventId()).orElseThrow(WtfException::new);
  }

  @GetMapping("/participations/registration")
  public Participation registerToEvent(@RequestParam(value = "userId") Long userId,
                                       @RequestParam(value= "eventId") Long eventId,
                                       @RequestParam(value= "pStatus") ParticipationStatus pStatus){

    Participation participation = new Participation();
    participation.setUserId(userId);
    participation.setEventId(eventId);
    participation.setStatus(pStatus);

    updateOrCreate(participation);

    return participation;
    }
  }
