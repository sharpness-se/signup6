package se.accelerateit.signup6.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import se.accelerateit.signup6.dao.ReminderMapper;
import se.accelerateit.signup6.model.Reminder;
import se.accelerateit.signup6.modelvalidator.EventDoesNotExistException;
import se.accelerateit.signup6.modelvalidator.FailedToSendRemindersException;
import se.accelerateit.signup6.reminder.ReminderSenderService;

import java.util.List;

@RestController
@Slf4j
public class ReminderController extends BaseApiController {

  private final ReminderSenderService reminderSenderService;
  private final ReminderMapper reminderMapper;

  @Autowired
  ReminderController(ReminderSenderService reminderSenderService, ReminderMapper reminderMapper) {
    this.reminderSenderService = reminderSenderService;
    this.reminderMapper = reminderMapper;
  }

  @GetMapping("/reminders/trigger")
  public List<Reminder> triggerReminders() {
    log.info("Externally triggered to send reminders!");
    try {
      List<Reminder> reminders = reminderSenderService.sendReminders();
      log.debug("Reminders completed.");
      return reminders;
    } catch (Exception ex) {
      log.error("Something went wrong when sending reminders", ex);
      throw new FailedToSendRemindersException(ex);
    }
  }

  @GetMapping("/reminders/findAllRemindersByEventId/{eventId}")
  public List<Reminder> findAllRemindersByEventId(@PathVariable(value = "eventId") Long eventId) {
    final var result = reminderMapper.findByEventId(eventId);
    if(!result.isEmpty()) {
      return result;
    } else {
      throw new EventDoesNotExistException();
    }
  }

}
