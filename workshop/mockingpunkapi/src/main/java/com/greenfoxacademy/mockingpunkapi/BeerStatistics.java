package com.greenfoxacademy.mockingpunkapi;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class BeerStatistics {
  private PunkAPI punkAPI;

  public BeerStatistics(PunkAPI punkAPI) {
    this.punkAPI = punkAPI;
  }

  public int count() throws UnirestException {
    return punkAPI.getBeers().size();
  }

  /**
   * Returns the beer with the largest ABV
   */
  public Beer strongest() throws UnirestException {
    return punkAPI.getBeers().stream()
            .max(Comparator.comparingDouble(b -> Double.parseDouble(b.getAbv())))
            .get();
  }

  /**
   * Returns the beer with the largest EBC
   */
  public Beer darkest() throws UnirestException {
    return punkAPI.getBeers().stream()
            .max(Comparator.comparingDouble(b -> b.getEbc() == null ? 0 : Double.parseDouble(b.getEbc())))
            .get();
  }
}
