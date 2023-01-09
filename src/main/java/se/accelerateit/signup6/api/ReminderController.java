package se.accelerateit.signup6.api;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.accelerateit.signup6.model.Reminder;
import se.accelerateit.signup6.modelvalidator.FailedToSendRemindersException;
import se.accelerateit.signup6.reminder.ReminderSenderService;

import java.util.List;

@RestController
@Log4j2
public class ReminderController extends BaseApiController {

  private final ReminderSenderService reminderSenderService;

  @Autowired
  ReminderController(ReminderSenderService reminderSenderService) {
    this.reminderSenderService = reminderSenderService;
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
}
