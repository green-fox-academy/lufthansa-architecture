package com.greenfoxacademy.myredditapi.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class APIConfiguration {
  @Value("${base_url:http://localhost:8080}")
  private String baseURL;

  public String getBaseURL() {
    return baseURL;
  }
}
