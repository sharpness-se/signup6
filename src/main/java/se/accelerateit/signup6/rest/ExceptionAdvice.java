package se.accelerateit.signup6.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import se.accelerateit.signup6.modelvalidator.NotMemberOfGroupException;

@ControllerAdvice
public class ExceptionAdvice {
  @ResponseBody
  @ExceptionHandler(NotMemberOfGroupException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String employeeNotFoundHandler(NotMemberOfGroupException ex) {
    return ex.getMessage();
  }
}
