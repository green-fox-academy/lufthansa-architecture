package com.greenfoxacademy.myredditapi.infrastructure;

import com.greenfoxacademy.myredditapi.models.*;
import com.greenfoxacademy.myredditapi.repositories.PostRepository;
import com.greenfoxacademy.myredditapi.repositories.SubredditRepository;
import com.greenfoxacademy.myredditapi.repositories.UserRepository;
import com.greenfoxacademy.myredditapi.repositories.UserVoteRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataSeed implements ApplicationRunner {
  private SubredditRepository subredditRepository;
  private PostRepository postRepository;
  private UserRepository userRepository;
  private UserVoteRepository userVoteRepository;

  public DataSeed(SubredditRepository subredditRepository,
                  PostRepository postRepository,
                  UserRepository userRepository,
                  UserVoteRepository userVoteRepository) {
    this.subredditRepository = subredditRepository;
    this.postRepository = postRepository;
    this.userRepository = userRepository;
    this.userVoteRepository = userVoteRepository;
  }

  @Override
  public void run(ApplicationArguments args) {
    if (userRepository.count() > 0) {
      return;
    }

    User user1 = new User("Petike");
    User user2 = new User("Petike");

    Subreddit subreddit1 = new Subreddit("programming");
    Subreddit subreddit2 = new Subreddit("fitness");

    Post post1 = new Post("First post title", "First post content", subreddit1);
    Post post2 = new Post("Second post title", "Second post content", subreddit1);
    Post post3 = new Post("Third post title", "Third post content", subreddit2);

    List<UserVote> votes = Arrays.asList(
      new UserVote(user1, post1, UserVoteDirection.UP),
      new UserVote(user1, post2, UserVoteDirection.UP),
      new UserVote(user2, post1, UserVoteDirection.UP),
      new UserVote(user2, post2, UserVoteDirection.DOWN),
      new UserVote(user2, post3, UserVoteDirection.UP)
    );

    userRepository.saveAll(Arrays.asList(user1, user2));
    subredditRepository.saveAll(Arrays.asList(subreddit1, subreddit2));
    postRepository.saveAll(Arrays.asList(post1, post2, post3));
    userVoteRepository.saveAll(votes);
  }
}
