package dev.vandael.jauth;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  public List<User> getUsers() {
    return userRepository.findAll();
  }

  public Optional<User> getUser(Long id) {
    return userRepository.findById(id);
  }

  public User createUser(User user) {
    return userRepository.saveAndFlush(user);
  }

  public User updateUser(Long id, User updatedUser) throws NotFoundException {
    User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
    user.setEmail(updatedUser.getEmail());
    user.setPassword(updatedUser.getPassword());
    user.setFirstName(updatedUser.getFirstName());
    user.setLastName(updatedUser.getLastName());
    return userRepository.saveAndFlush(user);
  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }
}
