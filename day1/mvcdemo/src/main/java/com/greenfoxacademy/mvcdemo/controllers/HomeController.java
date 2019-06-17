package com.greenfoxacademy.mvcdemo.controllers;

import com.greenfoxacademy.mvcdemo.models.Food;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class HomeController {

  private EntityManager entityManager;

  public HomeController(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @GetMapping("/")
  public String index(Model model) {
    Query query = entityManager.createQuery("select food from Food food"); // JPQL

    List foods = query.getResultList();

    model.addAttribute("foods", foods);

    return "index";
  }

  @PostMapping("/save")
  @Transactional
  public String index(Food food) {

    food.setAddedAt(LocalDateTime.now(Clock.systemUTC()));

    entityManager.persist(food);

    return "index";
  }
}
