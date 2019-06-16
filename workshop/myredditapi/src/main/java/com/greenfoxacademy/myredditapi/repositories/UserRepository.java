package com.greenfoxacademy.myredditapi.repositories;

import com.greenfoxacademy.myredditapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
