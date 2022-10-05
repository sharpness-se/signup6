package se.accelerateit.signup6.email;

public interface EmailSenderService {
    void send(String to, String subject, String message);
}
