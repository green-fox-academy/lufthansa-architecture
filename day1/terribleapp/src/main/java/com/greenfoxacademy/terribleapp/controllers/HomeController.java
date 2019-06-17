package com.greenfoxacademy.terribleapp.controllers;

import com.greenfoxacademy.terribleapp.services.BlogPostService;
import com.greenfoxacademy.terribleapp.services.PostsAndTags;
import com.greenfoxacademy.terribleapp.views.HomeView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {


  private BlogPostService blogPostService;

  public HomeController(BlogPostService blogPostService) {
    this.blogPostService = blogPostService;
  }

  @ResponseBody
  @GetMapping("/")
  public String index(@RequestParam(required = false) String tag) {
    PostsAndTags result = blogPostService.getPostsBasedOnTag(tag);

    HomeView view = new HomeView();
    return view.index(result.tags, result.posts);
  }

}
