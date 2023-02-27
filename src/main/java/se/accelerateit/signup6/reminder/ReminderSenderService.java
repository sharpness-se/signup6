package se.accelerateit.signup6.reminder;

import freemarker.template.TemplateException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import se.accelerateit.signup6.dao.EventMapper;
import se.accelerateit.signup6.dao.LogEntryMapper;
import se.accelerateit.signup6.dao.ReminderMapper;
import se.accelerateit.signup6.dao.UserMapper;
import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.model.LogEntry;
import se.accelerateit.signup6.model.ParticipationStatus;
import se.accelerateit.signup6.model.Reminder;
import se.accelerateit.signup6.model.User;
import se.accelerateit.signup6.modelvalidator.EventDoesNotExistException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Log4j2
public class ReminderSenderService {

    private final EventMapper eventMapper;
    private final ReminderMapper reminderMapper;
    private final UserMapper userMapper;

    private final LogEntryMapper logEntryMapper;
    private final JavaMailSender mailSender;
    private final MockMailSender mockMailSender;
    private final MessageBuilder messageBuilder;

    @Value("${signup.activate.mock.email}")
    private boolean mockEmailActivated;

    @Autowired
    public ReminderSenderService(EventMapper eventMapper, UserMapper userMapper, ReminderMapper reminderMapper, LogEntryMapper logEntryMapper, JavaMailSender mailSender, MockMailSender mockMailSender, MessageBuilder messageBuilder) {
        this.eventMapper = eventMapper;
        this.userMapper = userMapper;
        this.reminderMapper = reminderMapper;
        this.logEntryMapper = logEntryMapper;
        this.mailSender = mailSender;
        this.mockMailSender = mockMailSender;
        this.messageBuilder = messageBuilder;
    }

    private List<User> findUsersToRemind(Event event) {
        List<User> unregisteredMembers = userMapper.findUnregisteredMembers(event);
        List<User> maybeMembers = userMapper.findMembersByStatus(ParticipationStatus.Maybe, event);
        return Stream.concat(unregisteredMembers.stream(), maybeMembers.stream()).collect(Collectors.toList());
    }

    private void sendReminderMails(List<User> users, Event event) throws MessagingException, TemplateException, IOException {
        log.debug("Have {} users to remind about the event '{}'", users.size(), event.getName());
        for (User user : users) {
            log.info("Sending reminder to " + user.getEmail());
            MimeMessage message = messageBuilder.createReminderMessage(user.getEmail(), "Påminnelse " + event.getName(), user, event);
            if (!mockEmailActivated) {
                mailSender.send(message);
            } else {
                mockMailSender.send(message);
            }
        }
    }

    public List<Reminder> sendReminders() throws MessagingException, TemplateException, IOException {
        List<Reminder> dueReminders = reminderMapper.findDueReminders(LocalDate.now());
        log.debug("Found {} due reminders", dueReminders.size());
        for (Reminder reminder : dueReminders) {
            Event event = eventMapper.findById(reminder.getEventId()).orElseThrow(EventDoesNotExistException::new);
            log.debug("Sending reminder for event '{}' (id={})", event.getName(), event.getId());
            List<User> usersToRemind = findUsersToRemind(event);
            sendReminderMails(usersToRemind, event);
            logEntryMapper.create(new LogEntry(event, "Skickat påminnelse/inbjudan till " + usersToRemind.size() + " medlemmar."));
            reminderMapper.delete(reminder);
        }
        return dueReminders;
    }
}
