package se.accelerateit.signup6.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import se.accelerateit.signup6.dao.EventMapper;
import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.modelvalidator.EventDoesNotExistException;

import java.time.LocalDate;
import java.util.List;


@RestController
public class EventController extends BaseApiController {
  private final EventMapper eventMapper;

  @Autowired
  EventController(EventMapper eventMapper) {
    this.eventMapper = eventMapper;
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
    final var result = eventMapper.findAllEventsByGroup(groupId);
    if(!result.isEmpty()) {
      return result;
    } else {
      throw new EventDoesNotExistException();
    }
  }

  @GetMapping("/events/findAllUpcomingEventsByGroupId/{groupId}")
  public List<Event> findAllUpComingEventsByGroup(@PathVariable(value = "groupId") Long groupId) {
    final var result = eventMapper.findAllUpcomingEventsByGroup(LocalDate.now(), groupId);
    if(!result.isEmpty()) {
      return result;
    } else {
      throw new EventDoesNotExistException();
    }
  }

  @GetMapping("/events/findUpcomingEventsByUser/{userId}")
  public List<Event> findUpcomingEventsByUser(@PathVariable(value = "userId") Long userId) {
    final var result = eventMapper.findUpcomingEventsByUser(LocalDate.now(), userId);
    if(!result.isEmpty()) {
      return result;
    } else {
      throw new EventDoesNotExistException();
    }
  }

}
