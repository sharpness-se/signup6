package se.accelerateit.signup6.scheduling;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.accelerateit.signup6.dao.EventMapper;
import se.accelerateit.signup6.dao.ReminderMapper;
import se.accelerateit.signup6.email.emailUtil.UserFilter;
import se.accelerateit.signup6.email.EmailSenderService;
import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.model.Reminder;
import se.accelerateit.signup6.model.User;
import se.accelerateit.signup6.modelvalidator.EventDoesNotExistException;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.List;

@Component
@Log4j2
public class ScheduledEvents {

    private final EventMapper eventMapper;
    private final EmailSenderService senderService;
    private final UserFilter userFilter;

    private final ReminderMapper reminderMapper;

    @Autowired
    public ScheduledEvents(EventMapper eventMapper, UserFilter userFilter, EmailSenderService senderService, ReminderMapper reminderMapper) {
        this.eventMapper = eventMapper;
        this.senderService = senderService;
        this.userFilter = userFilter;
        this.reminderMapper = reminderMapper;
    }

    public void sendReminders() throws MessagingException {
        List<Reminder> dueReminders = getDueReminders();
        log.debug("Found {} due reminders", dueReminders.size());
        for (Reminder reminder : dueReminders) {
            Long eventId = reminder.getEventId();
            Event event = eventMapper.findById(eventId).orElseThrow(EventDoesNotExistException::new);
            log.debug("Sending reminder for event {}", event);
            List<User> usersToRemind = userFilter.getUsersToRemind(event);
            senderService.sendReminders(usersToRemind, event);
            reminderMapper.delete(reminder);
        }
    }

    private List<Event> getUpcomingEvents(){
        return eventMapper.findAllUpcomingEvents(LocalDate.now());
    }

    private List<Reminder> getDueReminders() {
        return reminderMapper.findDueReminders(LocalDate.now());
    }

}
