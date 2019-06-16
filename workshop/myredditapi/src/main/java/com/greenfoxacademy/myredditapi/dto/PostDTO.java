package com.greenfoxacademy.myredditapi.dto;

public class PostDTO {
  public long id;
  public String title;
  public String url;
  public long subredditId;
  public String subredditURL;
  public int score;

  public PostDTO(long id, String title, String url, long subredditId, String subredditURL) {
    this.id = id;
    this.title = title;
    this.url = url;
    this.subredditId = subredditId;
    this.subredditURL = subredditURL;
  }

  public PostDTO() {
  }

  public String getTitle() {
    return title;
  }

  public String getUrl() {
    return url;
  }
}
