package se.accelerateit.signup6.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import se.accelerateit.signup6.dao.EventMapper;
import se.accelerateit.signup6.dao.ParticipationMapper;
import se.accelerateit.signup6.dao.UserMapper;
import se.accelerateit.signup6.model.*;
import se.accelerateit.signup6.modelvalidator.EventDoesNotExistException;
import se.accelerateit.signup6.modelvalidator.EventValidator;
import se.accelerateit.signup6.modelvalidator.NotMemberOfGroupException;
import se.accelerateit.signup6.modelvalidator.WtfException;

import java.util.List;

@RestController
public class ParticipationController extends BaseApiController {
  private final ParticipationMapper participationMapper;
  private final UserMapper userMapper;
  private final EventMapper eventMapper;
  private final EventValidator eventValidator;

  @Value("${signup.frontend.base.url}")
  private String frontendBaseUrl;

  @Autowired
  ParticipationController(ParticipationMapper participationMapper, UserMapper userMapper, EventMapper eventMapper, EventValidator eventValidator) {
    this.participationMapper = participationMapper;
    this.userMapper = userMapper;
    this.eventMapper = eventMapper;
    this.eventValidator = eventValidator;
  }

  @GetMapping("/participations/findParticipationByEvent/{eventId}")
  public RegistrationStatus findParticipationStatusByEvent(@PathVariable(value = "eventId") Long eventId) {
    Event event = eventMapper.findById(eventId).orElseThrow(EventDoesNotExistException::new);
    RegistrationStatus registrationStatus = new RegistrationStatus();
    registrationStatus.unregisteredCounter = userMapper.findUnregisteredMembers(eventId).size();
    registrationStatus.onCounter = userMapper.findMembersByStatus(ParticipationStatus.On, event).size();
    registrationStatus.offCounter = userMapper.findMembersByStatus(ParticipationStatus.Off, event).size();
    registrationStatus.maybeCounter = userMapper.findMembersByStatus(ParticipationStatus.Maybe, event).size();

    return registrationStatus;

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
  public RedirectView registerToEvent(@RequestParam(value = "userId") Long userId,
                                      @RequestParam(value= "eventId") Long eventId,
                                      @RequestParam(value= "pStatus") ParticipationStatus pStatus){

    var p = participationMapper.findByUserAndEvent(userId, eventId);
    if(p.isPresent()) {
      var existingParticipation = p.get();
      existingParticipation.setStatus(pStatus); // 2nd time clicking in email, just update status
      participationMapper.update(existingParticipation);
    } else if(eventValidator.isMemberOfGroupForEvent(userId, eventId)){
      participationMapper.create(new Participation(pStatus, userId, eventId));
    } else {
      throw new NotMemberOfGroupException();
    }

    var url = frontendBaseUrl + "/participations/edit?eventId=" + eventId + "&userId=" + userId;
    return new RedirectView(url, false);
  }
}
