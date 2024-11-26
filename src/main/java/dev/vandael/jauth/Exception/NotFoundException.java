package dev.vandael.jauth.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {
  public NotFoundException() {}

  @Override
  public String getMessage() {
    return "NOT_FOUND";
  }
}
