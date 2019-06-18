package com.greenfoxacademy.myredditapi.services;

import com.greenfoxacademy.myredditapi.infrastructure.FakeSession;
import com.greenfoxacademy.myredditapi.models.User;
import com.greenfoxacademy.myredditapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
  private UserRepository userRepository;
  private FakeSession session;

  public UserService(UserRepository userRepository, FakeSession session) {
    this.userRepository = userRepository;
    this.session = session;
  }

  public User getCurrentUser() {
    Optional<User> optionalUser = userRepository.findByName(session.get("username"));

    return optionalUser.orElse(null);
  }

  public User loginOrCreateUser(String name) {
    Optional<User> optionalUser = userRepository.findByName(name);

    User user = null;
    if (!optionalUser.isPresent()) {
      user = new User(name);
      userRepository.save(user);
    }

    session.set("username", name);
    return user;
  }
}
