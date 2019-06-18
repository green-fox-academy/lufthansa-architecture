package com.greenfoxacademy.myredditapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SubredditEndpointTest {

  @Autowired
  TestRestTemplate restTemplate;

  @Test
  public void getSubredditsEndpointShouldReturnValidResult() {
    // Act
    ResponseEntity<List> responseEntity = restTemplate.getForEntity("/api/subreddits", List.class);

    List body = responseEntity.getBody();

    // Assert
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(2, body.size());
    assertEquals("programming", ((Map)body.get(0)).get("name"));
    assertEquals("fitness", ((Map)body.get(1)).get("name"));
  }
}
