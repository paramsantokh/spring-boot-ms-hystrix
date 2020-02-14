package com.jdi.test.springbootms.controller.service;

import com.jdi.test.springbootms.model.Rating;
import com.jdi.test.springbootms.model.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class UserRatingInfo {
  
  @Autowired
  RestTemplate restTemplate;
  
  @HystrixCommand (fallbackMethod = "getFallBackMovieRating",
      commandProperties = {
          @HystrixProperty (name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
          @HystrixProperty (name = "circuitBreaker.requestVolumeThreshold", value = "5"),
          @HystrixProperty (name = "circuitBreaker.errorThresholdPercentage", value = "50"),
          @HystrixProperty (name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
      })
  public UserRating getMovieRating(String userId) {
    return restTemplate.getForObject("http://movie-rating-service/ratings/users/" + userId, UserRating.class);
  }
  
  public UserRating getFallBackMovieRating(String userId) {
    UserRating rating = new UserRating();
    rating.setRatings(Arrays.asList(new Rating(userId, "error", 0)));
    return rating;
  }
  
  @HystrixCommand (fallbackMethod = "getFallBackAllRatings")
  public UserRating getAllRatings() {
    return restTemplate.getForObject("http://movie-rating-service/ratings/", UserRating.class);
  }
  
  public UserRating getFallBackAllRatings() {
    UserRating rating = new UserRating();
    rating.setRatings(Arrays.asList(new Rating("error", "error", 0)));
    return rating;
  }
}
