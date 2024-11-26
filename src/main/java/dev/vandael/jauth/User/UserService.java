package dev.vandael.jauth.User;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import dev.vandael.jauth.Exception.NotFoundException;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  public List<User> getUsers() {
    return userRepository.findAll();
  }

  public Optional<User> getUserById(Long id) {
    return userRepository.findById(id);
  }

  public User getUserByEmail(String email) throws NotFoundException {
    try {
      return userRepository.findByEmail(email).getFirst();
    } catch (NoSuchElementException _) {
      throw new NotFoundException();
    }
  }

  public User createUser(User user) {
    return userRepository.saveAndFlush(user);
  }

  public User updateUser(Long id, User updatedUser) throws NotFoundException {
    User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
    String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
    user.setEmail(updatedUser.getEmail());
    user.setPassword(hashed);
    user.setFirstName(updatedUser.getFirstName());
    user.setLastName(updatedUser.getLastName());
    return userRepository.saveAndFlush(user);
  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }
}
