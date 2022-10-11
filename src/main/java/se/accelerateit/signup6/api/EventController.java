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

  @GetMapping("/events/funky")
  public List<Event> testLmao(){
    return eventMapper.findAllUpcomingEvents(LocalDate.now().minusYears(2));
  }
}
