package se.accelerateit.signup6.email;

import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.model.User;

import java.util.List;

public interface EmailSenderService {
    void send(String to, String subject, String message);
    void sendReminders(List<User> users, Event event);
}
