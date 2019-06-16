package com.greenfoxacademy.mockingpunkapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PunkAPI {
  public PunkAPI() {
    setup();
  }

  private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
          = new com.fasterxml.jackson.databind.ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;

  public List<Beer> getBeers() throws UnirestException {
    HttpResponse<List> bookResponse = Unirest.get("https://api.punkapi.com/v2/beers").asObject(List.class);

    return (List<Beer>)bookResponse.getBody()
            .stream()
            .map(j -> jacksonObjectMapper.convertValue(j, Beer.class))
            .collect(Collectors.toList());
  }

  private void setup() {
    Unirest.setObjectMapper(new ObjectMapper() {
      private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
              = new com.fasterxml.jackson.databind.ObjectMapper();

      public <T> T readValue(String value, Class<T> valueType) {
        try {
          return jacksonObjectMapper.readValue(value, valueType);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }

      public String writeValue(Object value) {
        try {
          return jacksonObjectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
          throw new RuntimeException(e);
        }
      }
    });
  }
}
