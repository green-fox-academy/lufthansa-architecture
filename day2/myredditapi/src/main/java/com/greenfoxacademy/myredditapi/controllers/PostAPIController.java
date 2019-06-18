package com.greenfoxacademy.myredditapi.controllers;

import com.greenfoxacademy.myredditapi.dto.PostDTO;
import com.greenfoxacademy.myredditapi.infrastructure.URLs;
import com.greenfoxacademy.myredditapi.models.UserVoteDirection;
import com.greenfoxacademy.myredditapi.services.PostService;
import com.greenfoxacademy.myredditapi.services.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(URLs.POST_API)
public class PostAPIController {

  private PostService postService;
  private VoteService voteService;

  public PostAPIController(PostService postService, VoteService voteService) {
    this.postService = postService;
    this.voteService = voteService;
  }

  @GetMapping
  public List<PostDTO> list() {
    return postService.findAll();
  }

  @PostMapping
  public ResponseEntity post(@RequestBody PostDTO postDTO) {
    postService.addPost(postDTO, postDTO.subredditId);

    return ResponseEntity.noContent().build();
  }


  @GetMapping("{id}")
  public ResponseEntity<PostDTO> get(@PathVariable(name = "id") long postId) {
    Optional<PostDTO> optionalPostDTO = postService.getPost(postId);

    if (!optionalPostDTO.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    PostDTO postDTO = optionalPostDTO.get();

    return ResponseEntity.ok(postDTO);
  }


  @PutMapping("{id}")
  public ResponseEntity put(@PathVariable(name = "id") long postId, @RequestBody PostDTO postDTO) {
    postService.updatePost(postId, postDTO);

    return ResponseEntity.noContent().build();
  }


  @DeleteMapping("{id}")
  public ResponseEntity delete(@PathVariable(name = "id") long postId) {
    postService.deletePost(postId);

    return ResponseEntity.noContent().build();
  }


  @PutMapping("{id}/vote")
  public ResponseEntity put(@PathVariable(name = "id") long postId, @RequestBody VoteInput voteInput) {

    String directionString = voteInput.direction;

    UserVoteDirection userVoteDirection = null;
    try {
      userVoteDirection = UserVoteDirection.valueOf(directionString.toUpperCase());
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }

    voteService.vote(postId, userVoteDirection);
    return ResponseEntity.noContent().build();
  }

  private static class VoteInput {
    public String direction;
  }
}
