package com.greenfoxacademy.myredditapi.models;

import javax.persistence.*;

@Entity
public class UserVote {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  private User user;

  @ManyToOne
  private Post post;

  private int direction;

  public UserVote(User user, Post post, UserVoteDirection direction) {
    this.user = user;
    this.post = post;
    this.setDirection(direction);
  }

  public UserVote() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Post getPost() {
    return post;
  }

  public void setPost(Post post) {
    this.post = post;
  }

  public UserVoteDirection getDirection() {
    return UserVoteDirection.fromInt(direction);
  }

  public void setDirection(UserVoteDirection direction) {
    this.direction = direction.getValue();
  }
}

