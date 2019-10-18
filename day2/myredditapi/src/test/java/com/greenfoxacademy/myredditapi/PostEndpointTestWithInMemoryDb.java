package com.greenfoxacademy.myredditapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.myredditapi.dto.PostDTO;
import com.greenfoxacademy.myredditapi.dto.PostReferenceDTO;
import com.greenfoxacademy.myredditapi.dto.SubredditDTO;
import com.greenfoxacademy.myredditapi.models.Post;
import com.greenfoxacademy.myredditapi.models.Subreddit;
import com.greenfoxacademy.myredditapi.repositories.PostRepository;
import com.greenfoxacademy.myredditapi.repositories.SubredditRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.number.OrderingComparison.lessThan;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class PostEndpointTestWithInMemoryDb {

  @Autowired
  TestRestTemplate restTemplate;

  @Autowired
  PostRepository postRepository;

  @Autowired
  SubredditRepository subredditRepository;

  @Autowired
  ObjectMapper objectMapper;

  @LocalServerPort
  int port;

  @Before
  public void setup() {
    RestAssured.port = port;

    Subreddit subreddit = new Subreddit("Test Subreddit");

    subredditRepository.save(subreddit);

//    postRepository.saveAll(Arrays.asList(
//            new Post("Test Post", "test", subreddit),
//            new Post("Test Post 2", "test2", subreddit)
//    ));
  }

  @Test
  public void getPosts_shouldReturnValidPosts() {
    ResponseEntity<List> response = restTemplate.getForEntity("/api/posts", List.class);

    List body = response.getBody();

    Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assert.assertEquals(2, body.size());
    Assert.assertEquals(1, ((Map)body.get(0)).get("id"));
    Assert.assertEquals("Test Post", ((Map)body.get(0)).get("title"));
  }


  @Test
  public void getPosts_shouldReturnValidPosts_withRestAssured() {
    given()
    .when()
            .get("/api/posts")
    .then()
            .statusCode(HttpStatus.OK.value())
            .body("[0].id", equalTo(1));
  }

  @Test
  public void getPosts_shouldReturnPostsWhichWherePreviouslyPosted() throws JsonProcessingException {
    PostDTO postDTO = new PostDTO(1, "Test post title", "url", 1L, "");

    String postJson = objectMapper.writeValueAsString(postDTO);

    given()
      .body(postJson)
      .contentType(ContentType.JSON)
    .when()
      .post("/api/posts")
    .then()
      .statusCode(HttpStatus.NO_CONTENT.value())
      .time(lessThan((2000L)));


    int id = given()
    .when()
      .get("/api/posts")
    .then()
      .log().all()
      .statusCode(HttpStatus.OK.value())
      .body("[0].title", equalTo("Test post title"))
    .extract()
      .body()
      .jsonPath()
      .get("[0].id");

    given()
      .body("{\n" +
            "    \"title\": \"Title has changed\"" +
            "}")
      .contentType(ContentType.JSON)
    .when()
      .put("/api/posts/" + id)
    .then()
    .statusCode(HttpStatus.NO_CONTENT.value());

    given()
    .when()
      .get("/api/posts")
    .then()
      .log().all()
      .statusCode(HttpStatus.OK.value())
      .body("[0].title", equalTo("Title has changed"));
  }
}
