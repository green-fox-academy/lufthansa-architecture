package com.greenfoxacademy.myredditapi.services;

import com.greenfoxacademy.myredditapi.dto.PostDTO;
import com.greenfoxacademy.myredditapi.models.Post;
import com.greenfoxacademy.myredditapi.models.Subreddit;
import com.greenfoxacademy.myredditapi.repositories.PostRepository;
import com.greenfoxacademy.myredditapi.repositories.SubredditRepository;
import com.greenfoxacademy.myredditapi.repositories.UserVoteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
  private SubredditRepository subredditRepository;
  private PostRepository postRepository;
  private UrlProvider urlProvider;
  private UserVoteRepository userVoteRepository;

  public PostService(SubredditRepository subredditRepository,
                     PostRepository postRepository,
                     UrlProvider urlProvider,
                     UserVoteRepository userVoteRepository) {
    this.subredditRepository = subredditRepository;
    this.postRepository = postRepository;
    this.urlProvider = urlProvider;
    this.userVoteRepository = userVoteRepository;
  }

  public List<PostDTO> findAll() {
    return postRepository.findAll().stream()
      .map(this::mapToPostDTO)
      .collect(Collectors.toList());
  }

  public Optional<PostDTO> getPost(long postId) {
    return postRepository.findById(postId).map(this::mapToPostDTO);
  }

  public void addPost(PostDTO postDTO, long subredditId) {
    Subreddit subreddit = subredditRepository.getOne(subredditId);

    Post post = new Post(postDTO.title, postDTO.url, subreddit);

    post.setAddedAt(Instant.now());

    postRepository.save(post);
  }

  public void updatePost(long postId, PostDTO postDTO) {
    Optional<Post> optionalPost = postRepository.findById(postId);

    if (!optionalPost.isPresent()) {
      throw new IllegalArgumentException("Unable to find post with the id: `" + postId + "`");
    }

    Post post = optionalPost.get();

    BeanUtils.copyProperties(postDTO, post);

    postRepository.save(post);
  }

  public void deletePost(long postId) {
    Optional<Post> optionalPost = postRepository.findById(postId);

    if (!optionalPost.isPresent()) {
      throw new IllegalArgumentException("Unable to find post with the id: `" + postId + "`");
    }

    postRepository.deleteById(postId);
  }

  private PostDTO mapToPostDTO(Post post) {
    PostDTO postDTO = new PostDTO(
      post.getId(),
      post.getTitle(),
      post.getUrl(),
      post.getSubreddit().getId(),
      urlProvider.subredditFetchUrl(post.getSubreddit()));

    postDTO.score = userVoteRepository.scoreOfPost(post).orElse(0);

    return postDTO;
  }
}
