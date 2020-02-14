package com.jdi.test.springbootms.controller;

import com.jdi.test.springbootms.controller.service.MovieInfo;
import com.jdi.test.springbootms.controller.service.UserRatingInfo;
import com.jdi.test.springbootms.model.Movie;
import com.jdi.test.springbootms.model.MovieCatalog;
import com.jdi.test.springbootms.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping ("/movie-catalog")
public class MovieCatalogResource {
  
  @Autowired
  private RestTemplate restTemplate;
  
  @Autowired
  private MovieInfo movieInfo;
  @Autowired
  private UserRatingInfo ratingInfo;
  
  @GetMapping ("/{userId}")
  public List<MovieCatalog> getMovieCatalog(@PathVariable String userId) {
    List<MovieCatalog> movieCatalogs = new ArrayList<>();
    UserRating userRating = ratingInfo.getMovieRating(userId);
    userRating.getRatings().forEach(rating -> {
      Movie movie = movieInfo.getMovieInfo(rating.getMovieId());
      movieCatalogs.add(new MovieCatalog(userId, movie.getMovieId(), movie.getName(), movie.getGenre(), movie.getDescription(), rating.getRating()));
    });
    
    return movieCatalogs;
  }
  
  @GetMapping
  public List<MovieCatalog> getMovieCatalog() {
    List<MovieCatalog> movieCatalogs = new ArrayList<>();
    UserRating userRating = ratingInfo.getAllRatings();
    userRating.getRatings().forEach(rating -> {
      Movie movie = movieInfo.getMovieInfo(rating.getMovieId());
      movieCatalogs.add(new MovieCatalog(rating.getUserId(), movie.getMovieId(), movie.getName(), movie.getGenre(), movie.getDescription(), rating.getRating()));
    });
    
    return movieCatalogs;
  }
}
