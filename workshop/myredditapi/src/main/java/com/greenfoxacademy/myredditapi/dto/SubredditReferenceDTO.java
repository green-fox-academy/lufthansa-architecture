package com.greenfoxacademy.myredditapi.dto;

public class SubredditReferenceDTO {
  public final String name;
  public final String subredditURL;
  public final long postCount;

  public SubredditReferenceDTO(String name, String subredditURL, long postCount) {
    this.name = name;
    this.subredditURL = subredditURL;
    this.postCount = postCount;
  }
}
