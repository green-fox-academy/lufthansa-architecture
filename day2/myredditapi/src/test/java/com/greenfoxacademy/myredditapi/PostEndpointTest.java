package com.greenfoxacademy.myredditapi;

import org.junit.Assert;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class PostEndpointTest {

  @Autowired
  TestRestTemplate restTemplate;

  @Test
  public void getPosts_shouldReturnValidPosts() {
    ResponseEntity<List> response = restTemplate.getForEntity("/api/posts", List.class);

    List body = response.getBody();

    Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assert.assertEquals(3, body.size());
    Assert.assertEquals(1, ((Map)body.get(0)).get("id"));
    Assert.assertEquals("First post title", ((Map)body.get(0)).get("title"));
  }
}
