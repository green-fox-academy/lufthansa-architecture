package com.greenfoxacademy.terribleapp.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
public class Tag {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;

  @ManyToMany
  Collection<Post> posts;

  public Tag() {
  }

  public Tag(String name) {
    this.name = name;
    this.posts = new HashSet<>();
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
