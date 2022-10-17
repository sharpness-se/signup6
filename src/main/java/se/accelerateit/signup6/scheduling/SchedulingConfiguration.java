package se.accelerateit.signup6.scheduling;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import se.accelerateit.signup6.dao.ParticipationMapper;
import se.accelerateit.signup6.email.EmailSenderService;
import se.accelerateit.signup6.email.emailUtil.EmailMessageBuilder;
import se.accelerateit.signup6.email.impl.EmailSenderServiceImpl;
import se.accelerateit.signup6.model.User;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name= "scheduling.enabled", matchIfMissing = true)
public class SchedulingConfiguration {

    private final ScheduledEvents scheduledEvents;

    @Autowired
    public SchedulingConfiguration(ScheduledEvents scheduledEvents){
        this.scheduledEvents = scheduledEvents;
    }

    @Scheduled(cron = "*/30 * * * * *") //(* = Sec | * = Min | * = Hour | * = Day | * = Month| * = DayOfWeeek)
    public void scheduledTrigger() throws MessagingException { //TODO Handle exception
        System.out.println("5 seconds has passed " + new Date());
        scheduledEvents.sendReminders();
    }
}
