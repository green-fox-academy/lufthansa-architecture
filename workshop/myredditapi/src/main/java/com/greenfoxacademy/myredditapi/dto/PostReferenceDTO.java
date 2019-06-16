package com.greenfoxacademy.myredditapi.dto;

public class PostReferenceDTO {
  public final String title;
  public final String postURL;

  public PostReferenceDTO(String title, String postURL) {
    this.title = title;
    this.postURL = postURL;
  }
}
