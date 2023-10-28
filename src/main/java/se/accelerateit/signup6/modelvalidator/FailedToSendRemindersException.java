package se.accelerateit.signup6.modelvalidator;

import org.springframework.http.HttpStatus;

public class FailedToSendRemindersException extends DataModelException {
  public FailedToSendRemindersException(Exception cause) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send reminders", cause);
  }
}
