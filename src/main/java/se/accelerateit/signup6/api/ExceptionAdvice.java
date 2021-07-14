package se.accelerateit.signup6.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import se.accelerateit.signup6.modelvalidator.EventDoesNotExistException;
import se.accelerateit.signup6.modelvalidator.NotMemberOfGroupException;
import se.accelerateit.signup6.modelvalidator.UserDoesNotExistException;
import se.accelerateit.signup6.modelvalidator.WtfException;

@ControllerAdvice
public class ExceptionAdvice {
  @ResponseBody
  @ExceptionHandler(NotMemberOfGroupException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String invalidMembershipHandler(NotMemberOfGroupException ex) {
    return ex.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(UserDoesNotExistException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String userDoesNotExistHandler(UserDoesNotExistException ex) {
    return ex.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(EventDoesNotExistException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String eventDoesNotExistHandler(EventDoesNotExistException ex) {
    return ex.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(WtfException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  String wtfHandler(WtfException ex) {
    return ex.getMessage();
  }
}
