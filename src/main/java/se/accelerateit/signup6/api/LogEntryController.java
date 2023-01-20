package se.accelerateit.signup6.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import se.accelerateit.signup6.dao.EventMapper;
import se.accelerateit.signup6.dao.LogEntryMapper;
import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.model.LogEntry;
import se.accelerateit.signup6.modelvalidator.EventDoesNotExistException;

import java.util.List;
import java.util.Optional;

@RestController
public class LogEntryController extends BaseApiController{

  private final LogEntryMapper logEntryMapper;
  private final EventMapper eventMapper;

  @Autowired
  LogEntryController(LogEntryMapper logEntryMapper, EventMapper eventMapper) {
    this.logEntryMapper = logEntryMapper;
    this.eventMapper = eventMapper;
  }

  @GetMapping("/logentry/findEntriesByEventId/{eventId}")
  public List<LogEntry> findLogEntriesByEventId(@PathVariable(value = "eventId") Long eventId) {
    Optional<Event> event = eventMapper.findById(eventId);
    Event event1;
    if (event.isPresent()) {
      event1 = event.get();
    } else {
      throw new EventDoesNotExistException();
    }
    return logEntryMapper.findByEvent(event1);



  }

}
