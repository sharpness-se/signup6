package se.accelerateit.signup6.scheduling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import se.accelerateit.signup6.model.Reminder;
import se.accelerateit.signup6.reminder.ReminderSenderService;

import java.util.List;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
@Slf4j
public class SchedulingConfiguration {

  private final ReminderSenderService reminderSenderService;

  @Autowired
  public SchedulingConfiguration(ReminderSenderService reminderSenderService) {
    this.reminderSenderService = reminderSenderService;
  }

  @Scheduled(cron = "${signup.reminder.schedule}")
  public void scheduledRemindersTrigger() {
    log.info("Time to send reminders!");
    try {
      List<Reminder> reminiders = reminderSenderService.sendReminders();
      log.debug("Processed {} reminders", reminiders.size());
    } catch (Exception ex) {
      log.error("Something went wrong when sending reminders", ex);
    }
  }
}
