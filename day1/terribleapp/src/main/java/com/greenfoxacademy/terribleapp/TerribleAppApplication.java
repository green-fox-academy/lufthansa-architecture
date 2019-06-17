package com.greenfoxacademy.terribleapp;

import com.greenfoxacademy.terribleapp.models.Post;
import com.greenfoxacademy.terribleapp.models.Tag;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class TerribleAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(TerribleAppApplication.class, args);
  }
}

@Component
@Transactional
class InitialDataSeed implements ApplicationRunner {

  private EntityManager entityManager;

  public InitialDataSeed(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    Tag generalTag = new Tag("general");
    Tag cookingTag = new Tag("cooking");
    Tag programmingTag = new Tag("programming");

    Post post1 = new Post("First blog post", "Body of the first post");
    Post post2 = new Post("Second blog post", "Body of the second post");
    Post post3 = new Post("Third blog post", "Body of the third post");

    Stream.of(
      post1,
      post2,
      post3
    ).forEach(entityManager::persist);

    generalTag.getPosts().add(post1);
    generalTag.getPosts().add(post2);

    cookingTag.getPosts().add(post2);

    programmingTag.getPosts().add(post3);

    Stream.of(
      generalTag,
      cookingTag,
      programmingTag
    ).forEach(entityManager::persist);
  }
}
