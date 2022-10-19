package se.accelerateit.signup6.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.accelerateit.signup6.dao.EventMapper;
import se.accelerateit.signup6.email.emailUtil.UserFilter;
import se.accelerateit.signup6.email.EmailSenderService;
import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.model.User;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.List;

@Component
public class ScheduledEvents {

    private final EventMapper eventMapper;
    private final EmailSenderService senderService;
    private final UserFilter userFilter;

    @Autowired
    public ScheduledEvents(EventMapper eventMapper, UserFilter userFilter, EmailSenderService senderService) {
        this.eventMapper = eventMapper;
        this.senderService = senderService;
        this.userFilter = userFilter;
    }

    public void sendReminders() throws MessagingException {
        List<Event> upcomingEvents = getUpcomingEvents();

        for (Event event : upcomingEvents) {
            List<User> usersToRemind = userFilter.getUsersToRemind(event);
            senderService.sendReminders(usersToRemind, event);
        }
    }

    private List<Event> getUpcomingEvents(){
        return eventMapper.findAllUpcomingEvents(LocalDate.now());
    }

}
