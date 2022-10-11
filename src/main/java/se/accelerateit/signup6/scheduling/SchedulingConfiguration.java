package se.accelerateit.signup6.scheduling;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import se.accelerateit.signup6.dao.ParticipationMapper;
import se.accelerateit.signup6.email.EmailSenderService;
import se.accelerateit.signup6.email.impl.EmailSenderServiceImpl;
import se.accelerateit.signup6.model.User;

import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name= "scheduling.enabled", matchIfMissing = true)
public class SchedulingConfiguration {

    EmailSenderServiceImpl emailSenderService = new EmailSenderServiceImpl(new JavaMailSenderImpl());

    @Scheduled(cron = "*/5 * * * * *")
    public void scheduledTrigger() {
        System.out.println("5 seconds has passed " + new Date());
    }

    public List<User> findUsersToRemind() {
        //Vi behöver en lista med Users från Memberships med hjälp av GroupId.
        //En lista med Users som inte finns med i Participations, genom att hämta Users som tillhör en grupp.
        //Jämför med lista av Participations som redan finns.
        //Hämta lista med users som svarat kanske -> sammanställ listorna == personer som ska få mail

        //Göra en metod som bygger mail -> bygg mail och skicka till varje mailadress som hämtas från
        //Listan med users som inte svarat eller har fyllt i kanske.

        //Hämta lista av users som inte har svarat. Hämta alla users som


        List<Membership> membershipList = MembershipMapper.findUsersByGroup();
        List<User> userList;
        List<Participation> participationList = ParticipationMapper.findByEventId(Event.ID);
        List<User> maybeUsersList;

        for (Membership membership : membershipList) {
            userList.add(UserMapper.findById(membership.getUserId()));
        }

        for (Participation participation: membershipList) {
            maybeUsersList.add(UserMapper.findById(participation.findByStatusAndEventId(EventId, Maybe)));
        }

    }

    @Scheduled(cron = "* * * * * *") //(* = Sec | * = Min | * = Hour | * = Day | * = Month| * = DayOfWeeek)
    public void reminderTrigger() {
    }

}
