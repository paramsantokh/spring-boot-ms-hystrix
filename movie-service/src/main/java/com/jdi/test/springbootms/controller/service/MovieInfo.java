package com.jdi.test.springbootms.controller.service;

import com.jdi.test.springbootms.model.Movie;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo {
  
  @Autowired
  RestTemplate restTemplate;
  
  @HystrixCommand(fallbackMethod = "getFallBackMovieInfo")
  public Movie getMovieInfo(String movieId){
   return restTemplate.getForObject("http://movie-info-service/movies/" + movieId, Movie.class);
  }
  
  public Movie getFallBackMovieInfo(String movieId){
    return new Movie(movieId, "error", "error", "error");
  }
}
