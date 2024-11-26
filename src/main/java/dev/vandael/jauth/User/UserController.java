package dev.vandael.jauth.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import dev.vandael.jauth.Exception.NotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
  @Autowired
  private UserService userService;

  @GetMapping
  public List<User> getUsers() {
    return userService.getUsers();
  }

  @GetMapping("/{id}")
  public User getUser(@PathVariable Long id) throws NotFoundException {
    return userService.getUserById(id).orElseThrow(NotFoundException::new);
  }

  @PutMapping("/{id}")
  public User updateUser(@PathVariable Long id, @RequestBody User user) throws NotFoundException {
    return userService.updateUser(id, user);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
  }
}
