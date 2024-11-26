package dev.vandael.jauth.Auth;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import dev.vandael.jauth.Exception.NotFoundException;
import dev.vandael.jauth.JWT.JWTProvider;
import dev.vandael.jauth.User.User;
import dev.vandael.jauth.User.UserService;

@Service
public class AuthService {
  @Autowired
  private UserService userService;
  @Autowired
  private JWTProvider jwtProvider;

  public User signup(User user) {
    String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
    user.setPassword(hashed);
    return userService.createUser(user);
  }

  public HashMap<String, String> login(User creds) throws NotFoundException {
    User user = userService.getUserByEmail(creds.getEmail());
    boolean valid = BCrypt.checkpw(creds.getPassword(), user.getPassword());
    if (!valid) {
      throw new NotFoundException();
    }
    try {
      String token = jwtProvider.generateToken(user);
      HashMap<String, String> response = new HashMap<>(1);
      response.put("token", token);
      return response;
    } catch (Exception _) {
      throw new NotFoundException();
    }
  }
}
