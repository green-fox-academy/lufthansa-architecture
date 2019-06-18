package com.greenfoxacademy.myredditapi.controllers;

import com.greenfoxacademy.myredditapi.infrastructure.FakeSession;
import com.greenfoxacademy.myredditapi.models.User;
import com.greenfoxacademy.myredditapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

  private final FakeSession session;
  private final UserService userService;

  public LoginController(FakeSession session, UserService userService) {
    this.session = session;
    this.userService = userService;
  }

  @PostMapping("login")
  public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
    if (StringUtils.isEmpty(loginRequest.name)) {
      return ResponseEntity.badRequest().build();
    }

    userService.loginOrCreateUser(loginRequest.name);

    return ResponseEntity.ok(new TokenResponse(session.getSessionId()));
  }

  @GetMapping("/api/users/me")
  public ResponseEntity<User> currentUser() {
    User user = userService.getCurrentUser();

    if (user == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    return ResponseEntity.ok(user);
  }

  private static class TokenResponse {
    public String authToken;

    public TokenResponse(String authToken) {
      this.authToken = authToken;
    }
  }

  private static class LoginRequest {
    public String name;
  }
}
