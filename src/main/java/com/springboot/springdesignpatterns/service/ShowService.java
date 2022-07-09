package com.springboot.springdesignpatterns.service;

// import com.springboot.springdesignpatterns.dto.ShowDTO;
import com.springboot.springdesignpatterns.model.Show;

public interface ShowService {

  Iterable<Show> findAllShows();

  Iterable<Show> findShowByTitle(String title);
  
  Show addShow(Integer user_id, Show show);

  public void removeShow(Integer user_id, Show show);
  
}
