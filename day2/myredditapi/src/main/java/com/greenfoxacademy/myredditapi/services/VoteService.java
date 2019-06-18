package com.greenfoxacademy.myredditapi.services;

import com.greenfoxacademy.myredditapi.models.Post;
import com.greenfoxacademy.myredditapi.models.User;
import com.greenfoxacademy.myredditapi.models.UserVote;
import com.greenfoxacademy.myredditapi.models.UserVoteDirection;
import com.greenfoxacademy.myredditapi.repositories.PostRepository;
import com.greenfoxacademy.myredditapi.repositories.UserVoteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteService {
  private UserService userService;
  private PostRepository postRepository;
  private UserVoteRepository userVoteRepository;

  public VoteService(UserService userService, PostRepository postRepository, UserVoteRepository userVoteRepository) {
    this.userService = userService;
    this.postRepository = postRepository;
    this.userVoteRepository = userVoteRepository;
  }

  public void vote(long postId, UserVoteDirection userVoteDirection) {
    User currentUser = userService.getCurrentUser();

    Optional<Post> optionalPost = postRepository.findById(postId);

    if (!optionalPost.isPresent()) {
      return;
    }

    Post post = optionalPost.get();

    Optional<UserVote> optionalUserVote = userVoteRepository.findByPostAndUser(post, currentUser);

    UserVote userVote = null;

    if (!optionalUserVote.isPresent()) {
      userVote = new UserVote(currentUser, post, userVoteDirection);
    } else {
      userVote = optionalUserVote.get();
      userVote.setDirection(userVoteDirection);
    }

    userVoteRepository.save(userVote);
  }
}
