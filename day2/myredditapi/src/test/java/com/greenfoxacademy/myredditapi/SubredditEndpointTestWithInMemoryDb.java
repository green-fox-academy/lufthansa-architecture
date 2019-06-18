package com.greenfoxacademy.myredditapi;

import com.greenfoxacademy.myredditapi.models.Subreddit;
import com.greenfoxacademy.myredditapi.repositories.SubredditRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.After;
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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class SubredditEndpointTestWithInMemoryDb {
  @Autowired
  TestRestTemplate restTemplate;

  @Autowired
  SubredditRepository subredditRepository;

  @LocalServerPort
  int port;

  @Before
  public void setup() {
    RestAssured.port = port;
  }

  @After
  public void teardown() {
    subredditRepository.deleteAll();
  }

  @Test
  public void getSubredditsEndpointShouldReturnValidResult() {
    // Arrange
    subredditRepository.saveAll(Arrays.asList(
            new Subreddit("test1"),
            new Subreddit("test2")
    ));

    // Act
    ResponseEntity<List> responseEntity = restTemplate.getForEntity("/api/subreddits", List.class);

    List body = responseEntity.getBody();

    // Assert
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals("Number of result elements should have been", 2, body.size());
    assertEquals("test1", ((Map)body.get(0)).get("name"));
    assertEquals("test2", ((Map)body.get(1)).get("name"));
  }

  @Test
  public void getSubredditsEndpointShouldReturnValidResult_withRESTAssured() {
    // Arrange
    subredditRepository.saveAll(Arrays.asList(
            new Subreddit("test1"),
            new Subreddit("test2")
    ));

    // Act
    given()
            .when()
            .get("/api/subreddits")
            .then()
            .statusCode(equalTo(HttpStatus.OK.value()))
            .body("", hasSize(2))
            .body("[0].name", equalTo("test1"))
            .body("[1].name", equalTo("test2"))
    ;
  }

  @Test
  public void getSubredditsEndpointShouldReturnValidResult_withSchemaValidation() {
    // Arrange
    subredditRepository.saveAll(Arrays.asList(
            new Subreddit("test1"),
            new Subreddit("test2")
    ));

    // Act
    given()
            .when()
            .get("/api/subreddits")
            .then()
            .statusCode(equalTo(HttpStatus.OK.value()))
            .body(matchesJsonSchemaInClasspath("subreddits-schema.json"))
    ;
  }

  @Test
  public void getSubredditsEndpointShouldReturnTheValueWithWasPosted() {
    // Act
    given()
            //.header("Content-Type", "application/json")
            //.header("X-AUTH-TOKEN", "token from login endpoint")
            .contentType(ContentType.JSON)
            .body("{ \"name\": \"test\" }")
    .when()
            .post("/api/subreddits")
    .then()
            .statusCode(equalTo(HttpStatus.NO_CONTENT.value()))
            .time(lessThan(2000L))
    ;

    given()
    .when()
            .get("/api/subreddits")
    .then()
            .log().all()
            .statusCode(equalTo(HttpStatus.OK.value()))
            .body("", hasSize(1))
            .body("[0].name", equalTo("test"))
            .time(lessThan(2000L))
    ;
  }
}
