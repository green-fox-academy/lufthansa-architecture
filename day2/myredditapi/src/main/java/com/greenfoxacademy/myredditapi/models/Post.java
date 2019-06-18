package com.greenfoxacademy.myredditapi.models;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String title;
  private String url;
  private Instant addedAt;

  @ManyToOne(optional = false)
  private Subreddit subreddit;

  public Post(String title, String url, Subreddit subreddit) {
    this.title = title;
    this.url = url;
    this.subreddit = subreddit;
  }

  public Post() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Subreddit getSubreddit() {
    return subreddit;
  }

  public void setSubreddit(Subreddit subreddit) {
    this.subreddit = subreddit;
  }

  public Instant getAddedAt() {
    return addedAt;
  }

  public void setAddedAt(Instant addedAt) {
    this.addedAt = addedAt;
  }

}
