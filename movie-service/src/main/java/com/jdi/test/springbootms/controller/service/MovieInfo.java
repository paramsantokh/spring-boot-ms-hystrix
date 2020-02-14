package com.jdi.test.springbootms.controller.service;

import com.jdi.test.springbootms.model.Movie;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo {
  
  @Autowired
  RestTemplate restTemplate;
  
  @HystrixCommand (fallbackMethod = "getFallBackMovieInfo",
      commandProperties = {
          @HystrixProperty (name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
          @HystrixProperty (name = "circuitBreaker.requestVolumeThreshold", value = "5"),
          @HystrixProperty (name = "circuitBreaker.errorThresholdPercentage", value = "50"),
          @HystrixProperty (name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
      })
  public Movie getMovieInfo(String movieId) {
    return restTemplate.getForObject("http://movie-info-service/movies/" + movieId, Movie.class);
  }
  
  public Movie getFallBackMovieInfo(String movieId) {
    return new Movie(movieId, "error", "error", "error");
  }
}
