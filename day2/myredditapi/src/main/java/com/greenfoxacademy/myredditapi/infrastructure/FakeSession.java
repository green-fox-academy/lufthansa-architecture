package com.greenfoxacademy.myredditapi.infrastructure;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;
import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

@Service
@Scope(value = SCOPE_REQUEST, proxyMode = TARGET_CLASS)
public class FakeSession {
  private static Map<String, Map<String, String>> sessionStore = new HashMap<>();
  private String sessionId;

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public String get(String key) {
    return sessionStore.computeIfAbsent(sessionId, k -> new HashMap<>()).get(key);
  }

  public void set(String key, String value) {
    sessionStore.computeIfAbsent(sessionId, k -> new HashMap<>()).put(key, value);
  }
}
