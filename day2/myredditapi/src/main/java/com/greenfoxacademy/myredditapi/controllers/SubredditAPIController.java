package com.greenfoxacademy.myredditapi.controllers;

import com.greenfoxacademy.myredditapi.dto.SubredditDTO;
import com.greenfoxacademy.myredditapi.dto.SubredditReferenceDTO;
import com.greenfoxacademy.myredditapi.infrastructure.URLs;
import com.greenfoxacademy.myredditapi.services.SubredditService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(URLs.SUBREDDIT_API)
public class SubredditAPIController {

  private SubredditService subredditService;

  public SubredditAPIController(SubredditService subredditService) {
    this.subredditService = subredditService;
  }

  @GetMapping
  public List<SubredditReferenceDTO> list() {
    return subredditService.listSubreddits();
  }

  @GetMapping("{id}")
  public ResponseEntity<SubredditDTO> get(@PathVariable(name = "id") long subredditId) {
    Optional<SubredditDTO> subredditOptional = subredditService.getSubreddit(subredditId);

    if (subredditOptional.isPresent()) {
      return ResponseEntity.ok(subredditOptional.get());
    }

    return ResponseEntity.noContent().build();
  }

  @PostMapping
  public ResponseEntity post(@RequestBody SubredditDTO subredditDTO) {
    if (StringUtils.isEmpty(subredditDTO.name)) {
      return ResponseEntity.badRequest().build();
    }

    subredditService.addSubreddit(subredditDTO);

    return ResponseEntity.noContent().build();
  }

  @PutMapping("{id}")
  public ResponseEntity put(@PathVariable(name = "id") long subredditId, @RequestBody SubredditDTO subredditDTO) {
    if (StringUtils.isEmpty(subredditDTO.name)) {
      return ResponseEntity.badRequest().build();
    }

    subredditService.updateSubreddit(subredditId, subredditDTO);

    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("{id}")
  public ResponseEntity delete(@PathVariable(name = "id") long subredditId) {
    subredditService.deleteSubreddit(subredditId);

    return ResponseEntity.noContent().build();
  }
}
