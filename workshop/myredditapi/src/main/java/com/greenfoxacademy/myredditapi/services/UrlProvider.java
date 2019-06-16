package com.greenfoxacademy.myredditapi.services;

import com.greenfoxacademy.myredditapi.infrastructure.APIConfiguration;
import com.greenfoxacademy.myredditapi.infrastructure.URLs;
import com.greenfoxacademy.myredditapi.models.Subreddit;
import org.springframework.stereotype.Service;

@Service
public class UrlProvider {

  private APIConfiguration apiConfiguration;

  public UrlProvider(APIConfiguration apiConfiguration) {
    this.apiConfiguration = apiConfiguration;
  }

  public String subredditFetchUrl(Subreddit subreddit) {
    return apiConfiguration.getBaseURL() + URLs.SUBREDDIT_API + "/" + subreddit.getId();
  }

  public String postFetchUrl(long postId) {
    return apiConfiguration.getBaseURL() + URLs.POST_API + "/" + postId;
  }
}
