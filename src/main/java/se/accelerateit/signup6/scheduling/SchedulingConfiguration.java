package se.accelerateit.signup6.scheduling;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.mail.MessagingException;
import java.util.Date;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name= "scheduling.enabled", matchIfMissing = true)
public class SchedulingConfiguration {

    private final ScheduledEvents scheduledEvents;

    @Autowired
    public SchedulingConfiguration(ScheduledEvents scheduledEvents){
        this.scheduledEvents = scheduledEvents;
    }

    @Scheduled(cron = "*/10 * * * * *") //(* = Sec | * = Min | * = Hour | * = Day | * = Month| * = DayOfWeeek)
    public void scheduledTrigger() throws MessagingException { //TODO Handle exception
        System.out.println("10 seconds has passed " + new Date());
        scheduledEvents.sendReminders();
    }
}
