package dev.vandael.jauth.Auth;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import dev.vandael.jauth.Exception.NotFoundException;
import dev.vandael.jauth.User.User;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1")
public class AuthController {
  @Autowired
  private AuthService authService;

  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.CREATED)
  public User signup(@RequestBody User user) {
    return authService.signup(user);
  }

  @PostMapping("/login")
  public HashMap<String, String> login(@RequestBody User creds) throws NotFoundException {
    return authService.login(creds);
  }
}
