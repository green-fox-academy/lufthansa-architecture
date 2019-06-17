package com.greenfoxacademy.terribleapp.views;

import com.greenfoxacademy.terribleapp.models.Post;
import com.greenfoxacademy.terribleapp.models.Tag;

import java.util.Collection;
import java.util.List;

public class HomeView {
  public String index(List<Tag> tags, Collection<Post> posts) {
    String responseBody = "";

    responseBody += "<h1>My Pretty Little Blog</h1>\n" +
            "<div>Welcome to my own blog!</div>";


    responseBody += "<h2>Tags</h2>\n" +
            "<ul>\n";

    for (Tag currentTag : tags) {
      responseBody += "    <li>\n" +
              "        <a href=\"?tag=" + currentTag.getName() + "\">" + currentTag.getName() + "</a>\n" +
              "    </li>\n";
    }

    responseBody += "</ul>";

    for (Post post : posts) {
      responseBody += "<div>\n" +
              "    <h2>" + post.getTitle() + "</h2>\n" +
              "    <div>" + post.getBody() + "</div>\n" +
              "</div>";
    }

    return responseBody;
  }
}
