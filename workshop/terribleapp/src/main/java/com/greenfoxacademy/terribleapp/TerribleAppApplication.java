package com.greenfoxacademy.terribleapp;

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

@Controller
class HomeController {

  private EntityManager entityManager;

  public HomeController(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @ResponseBody
  @GetMapping("/")
  public String index(@RequestParam(required = false) String tag) {
    String responseBody = "";

    responseBody += "<h1>My Pretty Little Blog</h1>\n" +
      "<div>Welcome to my own blog!</div>";

    Query tagQuery = entityManager.createQuery("SELECT tag from Tag tag");

    List<Tag> tags = ((Stream<Tag>) tagQuery.getResultStream().map(Tag.class::cast)).collect(Collectors.toList());

    responseBody += "<h2>Tags</h2>\n" +
      "<ul>\n";

    for (Tag currentTag : tags) {
      responseBody += "    <li>\n" +
        "        <a href=\"?tag=" + currentTag.getName() + "\">" + currentTag.getName() + "</a>\n" +
        "    </li>\n";
    }

    responseBody += "</ul>";

    Collection<Post> posts = null;
    if (!StringUtils.isEmpty(tag)) {
      Optional<Tag> optionalTag = tags.stream()
        .filter(t -> t.getName().equals(tag))
        .findFirst();

      if (optionalTag.isPresent()) {
        Tag selectedTag = optionalTag.get();

        posts = selectedTag.posts;
      }
    } else {
      Query query = entityManager.createQuery("SELECT post from Post post");
      posts = query.getResultList();
    }

    for (Post post : posts) {
      responseBody += "<div>\n" +
        "    <h2>" + post.getTitle() + "</h2>\n" +
        "    <div>" + post.getBody() + "</div>\n" +
        "</div>";
    }

    return responseBody;
  }
}

@Entity
class Tag {
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
}

@Entity
class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String title;
  private String body;

  public Post() {
  }

  public Post(String title, String body) {
    this.title = title;
    this.body = body;
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

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
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

    generalTag.posts.add(post1);
    generalTag.posts.add(post2);

    cookingTag.posts.add(post2);

    programmingTag.posts.add(post3);

    Stream.of(
      generalTag,
      cookingTag,
      programmingTag
    ).forEach(entityManager::persist);
  }
}
