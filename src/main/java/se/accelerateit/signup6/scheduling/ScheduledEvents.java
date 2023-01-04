package se.accelerateit.signup6.scheduling;

import freemarker.template.TemplateException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.accelerateit.signup6.dao.EventMapper;
import se.accelerateit.signup6.dao.ReminderMapper;
import se.accelerateit.signup6.dao.UserMapper;
import se.accelerateit.signup6.email.EmailSenderService;
import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.model.ParticipationStatus;
import se.accelerateit.signup6.model.Reminder;
import se.accelerateit.signup6.model.User;
import se.accelerateit.signup6.modelvalidator.EventDoesNotExistException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Log4j2
public class ScheduledEvents {

    private final EventMapper eventMapper;
    private final ReminderMapper reminderMapper;
    private final UserMapper userMapper;

    private final EmailSenderService senderService;

    @Autowired
    public ScheduledEvents(EventMapper eventMapper, UserMapper userMapper, ReminderMapper reminderMapper, EmailSenderService senderService) {
        this.eventMapper = eventMapper;
        this.userMapper = userMapper;
        this.reminderMapper = reminderMapper;
        this.senderService = senderService;
    }

    private List<User> findUsersToRemind(Event event) {
        List<User> unregisteredMembers = userMapper.findUnregisteredMembers(event);
        List<User> maybeMembers = userMapper.findMembersByStatus(ParticipationStatus.Maybe, event);
        return Stream.concat(unregisteredMembers.stream(), maybeMembers.stream()).collect(Collectors.toList());
    }

    public void sendReminders() throws MessagingException, TemplateException, IOException {
        List<Reminder> dueReminders = reminderMapper.findDueReminders(LocalDate.now());
        log.debug("Found {} due reminders", dueReminders.size());
        for (Reminder reminder : dueReminders) {
            Event event = eventMapper.findById(reminder.getEventId()).orElseThrow(EventDoesNotExistException::new);
            log.debug("Sending reminder for event '{}' (id={})", event.getName(), event.getId());
            List<User> usersToRemind = findUsersToRemind(event);
            senderService.sendReminders(usersToRemind, event);
            reminderMapper.delete(reminder);
        }
    }
}
