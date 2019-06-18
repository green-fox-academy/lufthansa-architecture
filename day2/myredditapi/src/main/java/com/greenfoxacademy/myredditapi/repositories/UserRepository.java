package com.greenfoxacademy.myredditapi.repositories;

import com.greenfoxacademy.myredditapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByName(String name);
}
