package com.greenfoxacademy.myredditapi.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Subreddit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;

  @OneToMany(mappedBy = "subreddit")
  private Collection<Post> posts;

  public Subreddit(String name) {
    this.name = name;
  }

  public Subreddit() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Collection<Post> getPosts() {
    return posts;
  }

  public void setPosts(Collection<Post> posts) {
    this.posts = posts;
  }
}
