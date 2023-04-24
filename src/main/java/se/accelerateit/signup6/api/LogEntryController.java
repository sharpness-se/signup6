package se.accelerateit.signup6.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import se.accelerateit.signup6.dao.LogEntryMapper;
import se.accelerateit.signup6.model.LogEntry;

import java.util.List;

@RestController
public class LogEntryController extends BaseApiController{

  private final LogEntryMapper logEntryMapper;

  @Autowired
  LogEntryController(LogEntryMapper logEntryMapper) {
    this.logEntryMapper = logEntryMapper;
  }

  @GetMapping("/logentry/findEntriesByEventId/{eventId}")
  public List<LogEntry> findLogEntriesByEventId(@PathVariable(value = "eventId") Long eventId) {
    return logEntryMapper.findByEvent(eventId);
  }

}
