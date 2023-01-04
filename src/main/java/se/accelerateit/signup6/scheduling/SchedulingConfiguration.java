package se.accelerateit.signup6.scheduling;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.mail.MessagingException;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
@Log4j2
public class SchedulingConfiguration {

  private final ScheduledEvents scheduledEvents;

  @Autowired
  public SchedulingConfiguration(ScheduledEvents scheduledEvents) {
    this.scheduledEvents = scheduledEvents;
  }

  @Scheduled(cron = "${signup.reminder.schedule}")
  public void scheduledTrigger() {
    log.info("Time to send reminders!");
    try {
      scheduledEvents.sendReminders();
      log.debug("Reminders completed.");
    } catch (Exception ex) {
      log.error("Something went wrong when sending reminders", ex);
    }
  }
}
