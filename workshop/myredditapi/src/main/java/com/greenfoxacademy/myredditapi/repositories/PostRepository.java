package com.greenfoxacademy.myredditapi.repositories;

import com.greenfoxacademy.myredditapi.models.Post;
import com.greenfoxacademy.myredditapi.models.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
  long countBySubreddit(Subreddit subreddit);

  @Query("SELECT p.id as id, p.title as title FROM Post p WHERE p.subreddit = ?1")
  List<SubredditPostView> findAllIdAndTitleBySubredditId(Subreddit subreddit);
}
