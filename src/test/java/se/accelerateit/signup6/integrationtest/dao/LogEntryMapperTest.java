package se.accelerateit.signup6.integrationtest.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.accelerateit.signup6.dao.EventMapper;
import se.accelerateit.signup6.dao.LogEntryMapper;
import se.accelerateit.signup6.integrationtest.SignupDbTest;
import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.model.LogEntry;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTest.Initializer.class})
public class LogEntryMapperTest extends SignupDbTest {
  private final EventMapper eventMapper;
  private final LogEntryMapper logEntryMapper;

  @Autowired
  LogEntryMapperTest(EventMapper eventMapper, LogEntryMapper logEntryMapper) {
    this.eventMapper = eventMapper;
    this.logEntryMapper = logEntryMapper;
  }

  @Test
  void createAndFindALogEntry() {
    long eventId = -1L;
    Event event = eventMapper.findById(eventId).orElseThrow();
    List<LogEntry> initialLogEntries = logEntryMapper.findByEvent(eventId);

    logEntryMapper.create(new LogEntry(event, "This is  a log message"));
    List<LogEntry> currentLogEntries = logEntryMapper.findByEvent(eventId);

    assertEquals(1, currentLogEntries.size()-initialLogEntries.size(), "Missing a log entry");

    LogEntry last = currentLogEntries.get(currentLogEntries.size()-1);
    assertEquals("This is  a log message", last.getMessage());
  }
}
