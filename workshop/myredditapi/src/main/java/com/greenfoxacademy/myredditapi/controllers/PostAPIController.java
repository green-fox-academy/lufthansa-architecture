package com.greenfoxacademy.myredditapi.controllers;

import com.greenfoxacademy.myredditapi.dto.PostDTO;
import com.greenfoxacademy.myredditapi.infrastructure.URLs;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(URLs.POST_API)
public class PostAPIController {
  @GetMapping
  public List<PostDTO> list() {
    // TODO implement this
    return null;
  }

  //@PostMapping


  //@GetMapping("{id}")


  //@PutMapping("{id}")


  //@DeleteMapping("{id}")
}
