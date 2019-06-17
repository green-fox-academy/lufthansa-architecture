package com.greenfoxacademy.terribleapp.services;

import com.greenfoxacademy.terribleapp.models.Post;
import com.greenfoxacademy.terribleapp.models.Tag;
import com.greenfoxacademy.terribleapp.repositories.PostRepository;
import com.greenfoxacademy.terribleapp.repositories.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService {
  private TagRepository tagRepository;
  private PostRepository postRepository;

  public BlogPostService(TagRepository tagRepository, PostRepository postRepository) {
    this.tagRepository = tagRepository;
    this.postRepository = postRepository;
  }

  public PostsAndTags getPostsBasedOnTag(String tag) {
    List<Tag> tags = tagRepository.findAllTags();

    Collection<Post> posts = null;
    if (!StringUtils.isEmpty(tag)) {
      Optional<Tag> optionalTag = tags.stream()
              .filter(t -> t.getName().equals(tag))
              .findFirst();

      if (optionalTag.isPresent()) {
        Tag selectedTag = optionalTag.get();

        posts = selectedTag.getPosts();
      }
    } else {
      posts = postRepository.findAllPosts();
    }

    return new PostsAndTags(tags, posts);
  }
}
