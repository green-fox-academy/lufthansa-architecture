package com.greenfoxacademy.myredditapi.dto;

import java.util.List;

public class SubredditDTO {
  public String name;
  public List<PostReferenceDTO> posts;

  public SubredditDTO(String name, List<PostReferenceDTO> posts) {
    this.name = name;
    this.posts = posts;
  }

  public SubredditDTO() {
  }

  public String getName() {
    return name;
  }

  public List<PostReferenceDTO> getPosts() {
    return posts;
  }
}
