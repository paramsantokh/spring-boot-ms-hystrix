package com.jdi.test.springbootms.controller.service;

import com.jdi.test.springbootms.model.Rating;
import com.jdi.test.springbootms.model.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class UserRatingInfo {
  
  @Autowired
  RestTemplate restTemplate;
  
  @HystrixCommand (fallbackMethod = "getFallBackMovieRating")
  public UserRating getMovieRating(String userId) {
    return restTemplate.getForObject("http://movie-rating-service/ratings/users/" + userId, UserRating.class);
  }
  
  public UserRating getFallBackMovieRating(String userId) {
    UserRating rating = new UserRating();
    rating.setRatings(Arrays.asList(new Rating(userId, "error", 0)));
    return rating;
  }
  
  @HystrixCommand (fallbackMethod = "getFallBackAllRatings")
  public UserRating getAllRatings(){
    return restTemplate.getForObject("http://movie-rating-service/ratings/", UserRating.class);
  }
  
  public UserRating getFallBackAllRatings() {
    UserRating rating = new UserRating();
    rating.setRatings(Arrays.asList(new Rating("error", "error", 0)));
    return rating;
  }
}
