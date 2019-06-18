package com.greenfoxacademy.myredditapi;

import com.greenfoxacademy.myredditapi.infrastructure.FakeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@SpringBootApplication
public class MyRedditApiApplication implements WebMvcConfigurer {

  public static void main(String[] args) {
    SpringApplication.run(MyRedditApiApplication.class, args);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(sessionInterceptor());
  }

  @Bean
  public SessionInterceptor sessionInterceptor() {
    return new SessionInterceptor();
  }
}

class SessionInterceptor extends HandlerInterceptorAdapter {

  @Autowired
  FakeSession fakeSession;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    String authToken = request.getHeader("X-AUTH-TOKEN");

    if (authToken == null) {
      authToken = UUID.randomUUID().toString();
    }

    fakeSession.setSessionId(authToken);
    return true;
  }
}
