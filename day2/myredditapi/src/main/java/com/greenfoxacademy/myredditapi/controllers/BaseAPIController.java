package com.greenfoxacademy.myredditapi.controllers;

import com.greenfoxacademy.myredditapi.infrastructure.APIConfiguration;
import com.greenfoxacademy.myredditapi.infrastructure.URLs;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BaseAPIController {
  private APIConfiguration apiConfiguration;

  public BaseAPIController(APIConfiguration apiConfiguration) {
    this.apiConfiguration = apiConfiguration;
  }

  @GetMapping
  public BaseApiResponse get() {
    return new BaseApiResponse(apiConfiguration.getBaseURL());
  }

  private static class BaseApiResponse {
    private final String baseURL;

    public BaseApiResponse(String baseURL) {
      this.baseURL = baseURL;
    }

    public String getSubredditsUrl() {
      return baseURL + URLs.SUBREDDIT_API;
    }

    public String getPostsUrl() {
      return baseURL + URLs.POST_API;
    }
  }
}
