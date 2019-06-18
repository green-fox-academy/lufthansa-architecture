package com.greenfoxacademy.myredditapi.repositories;

import com.greenfoxacademy.myredditapi.models.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
  Optional<Subreddit> getOneByName(String name);
}
