package com.greenfoxacademy.myredditapi.repositories;

import com.greenfoxacademy.myredditapi.models.Post;
import com.greenfoxacademy.myredditapi.models.UserVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserVoteRepository extends JpaRepository<UserVote, Long> {
  @Query("select sum(uv.direction) from UserVote uv where uv.post = :post")
  Optional<Integer> scoreOfPost(@Param("post") Post post);
}
