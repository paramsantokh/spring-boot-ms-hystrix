package com.jdi.test.movieratingservice.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Id class is used to make the composite keys
 */
public class RatingId implements Serializable {
  private String userId;
  private String movieId;
  
  public RatingId(){
  
  }
  
  public RatingId(String userId, String movieId) {
    this.userId = userId;
    this.movieId = movieId;
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RatingId ratingId = (RatingId) o;
    return Objects.equals(userId, ratingId.userId) &&
           Objects.equals(movieId, ratingId.movieId);
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(userId, movieId);
  }
}
