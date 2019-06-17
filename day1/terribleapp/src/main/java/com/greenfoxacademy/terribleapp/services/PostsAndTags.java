package com.greenfoxacademy.terribleapp.services;

import com.greenfoxacademy.terribleapp.models.Post;
import com.greenfoxacademy.terribleapp.models.Tag;

import java.util.Collection;
import java.util.List;

public class PostsAndTags {
  public List<Tag> tags;
  public Collection<Post> posts;

  public PostsAndTags(List<Tag> tags, Collection<Post> posts) {
    this.tags = tags;
    this.posts = posts;
  }
}
