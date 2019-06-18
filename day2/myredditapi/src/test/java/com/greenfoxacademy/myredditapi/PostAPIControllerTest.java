package com.greenfoxacademy.myredditapi;

import com.greenfoxacademy.myredditapi.controllers.PostAPIController;
import com.greenfoxacademy.myredditapi.dto.PostDTO;
import com.greenfoxacademy.myredditapi.infrastructure.FakeSession;
import com.greenfoxacademy.myredditapi.services.PostService;
import com.greenfoxacademy.myredditapi.services.UserService;
import com.greenfoxacademy.myredditapi.services.VoteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PostAPIController.class)
@AutoConfigureMockMvc
public class PostAPIControllerTest {
  @Autowired
  MockMvc mockMvc;

  @MockBean
  private PostService postService;

  @MockBean
  private VoteService voteService;

  @MockBean
  private UserService userService;

  @MockBean
  private FakeSession fakeSession;

  @Test
  public void getShouldReturnsNotFound() throws Exception {
    mockMvc.perform(get("/api/posts/1"))
            .andExpect(status().isNotFound());
  }

  @Test
  public void getShouldReturnValidPosts() throws Exception {
    Mockito.when(postService.getPost(anyLong())).thenReturn(Optional.of(new PostDTO()));

    mockMvc.perform(get("/api/posts/1"))
            .andExpect(status().isOk());
  }
}
