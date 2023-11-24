package se.accelerateit.signup6.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.accelerateit.signup6.dao.EventMapper;
import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.modelvalidator.EventDoesNotExistException;
import se.accelerateit.signup6.modelvalidator.MissingParametersException;
import se.accelerateit.signup6.modelvalidator.WtfException;

import java.time.LocalDate;
import java.util.List;


@RestController
public class EventController extends BaseApiController {
  private final EventMapper eventMapper;

  @Autowired
  EventController(EventMapper eventMapper) {
    this.eventMapper = eventMapper;
  }

  @PostMapping("/events/create")
  public Event createEvent(@RequestBody Event event) {
    try {
      eventMapper.createEvent(event);
      return eventMapper.findById(event.getId()).orElseThrow(WtfException::new);
    } catch (DataIntegrityViolationException e) {
      throw new MissingParametersException(e);
    }
  }

  @GetMapping("/events/{eventId}")
  public Event find(@PathVariable(value = "eventId") Long eventId) {
    final var result = eventMapper.findById(eventId);
    if(result.isPresent()) {
      return result.get();
    } else {
      throw new EventDoesNotExistException();
    }
  }

  @GetMapping("/events/findAllEventsByGroupId/{groupId}")
  public List<Event> findAllByGroup(@PathVariable(value = "groupId") Long groupId) {
    return eventMapper.findAllEventsByGroup(groupId);
  }

  @GetMapping("/events/findAllUpcomingEventsByGroupId/{groupId}")
  public List<Event> findAllUpComingEventsByGroup(@PathVariable(value = "groupId") Long groupId) {
    return eventMapper.findAllUpcomingEventsByGroup(LocalDate.now(), groupId);
  }

  @GetMapping("/events/findUpcomingEventsByUser/{userId}")
  public List<Event> findUpcomingEventsByUser(@PathVariable(value = "userId") Long userId) {
    return eventMapper.findUpcomingEventsByUser(LocalDate.now(), userId);
  }

}
