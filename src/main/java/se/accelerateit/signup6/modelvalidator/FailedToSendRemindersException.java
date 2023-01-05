package se.accelerateit.signup6.modelvalidator;

public class FailedToSendRemindersException extends RuntimeException {
  public FailedToSendRemindersException(Exception cause) {
    super("Failed to send reminders", cause);
  }
}
