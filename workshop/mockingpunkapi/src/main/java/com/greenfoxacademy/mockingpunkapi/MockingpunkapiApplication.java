package com.greenfoxacademy.mockingpunkapi;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MockingpunkapiApplication implements CommandLineRunner {

  private BeerStatistics beerStatistics;

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(MockingpunkapiApplication.class, args);
  }

  public MockingpunkapiApplication(BeerStatistics beerStatistics) {
    this.beerStatistics = beerStatistics;
  }

  @Override
  public void run(String... args) throws UnirestException {
    System.out.println("Number of beers: " + beerStatistics.count());
    System.out.println("Strongest beer: " + beerStatistics.strongest().getName());
    System.out.println("Darkest beer: " + beerStatistics.darkest().getName());
  }
}
