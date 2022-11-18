package se.accelerateit.signup6.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.accelerateit.signup6.dao.EventMapper;
import se.accelerateit.signup6.dao.ReminderMapper;
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

    private final ReminderMapper reminderMapper;

    @Autowired
    public ScheduledEvents(EventMapper eventMapper, UserFilter userFilter, EmailSenderService senderService) {
        this.eventMapper = eventMapper;
        this.senderService = senderService;
        this.userFilter = userFilter;
    }

    public void sendReminders() throws MessagingException {
        // TODO get all due reminders
        // TODO extract all events that are due

        List<Reminder> dueReminders = getDueReminders();
        System.out.println("Reminder list size: " + dueReminders.size());
        for (Reminder reminder : dueReminders) {
            System.out.println("Current reminder ID: " + reminder.getId());
            Long eventId = reminder.getEventId();
            Event event = eventMapper.findById(eventId).get(); // TODO isPresent ?
            System.out.println("Current event: " + event.getId());
            List<User> usersToRemind = userFilter.getUsersToRemind(event);
            System.out.println("Number of users to email: " + usersToRemind.size());
            senderService.sendReminders(usersToRemind, event);
            reminderMapper.delete(reminder);
        }

        // TODO remove all used/due reminders
    }

    private List<Event> getUpcomingEvents(){
        return eventMapper.findAllUpcomingEvents(LocalDate.now());
    }

    private List<Reminder> getDueReminders() {return reminderMapper.findDueReminders(LocalDate.now());}

}
