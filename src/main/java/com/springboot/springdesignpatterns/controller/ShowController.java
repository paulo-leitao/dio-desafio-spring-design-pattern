package com.springboot.springdesignpatterns.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.springboot.springdesignpatterns.dto.ShowDTO;
import com.springboot.springdesignpatterns.model.Show;
import com.springboot.springdesignpatterns.service.ShowService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("show")
public class ShowController {
  @Autowired
  private ShowService service;
  
  
  @Operation(summary = "Get all shows from the database.")
  @GetMapping
  public ResponseEntity<Iterable<Show>> findAllShows() {
    return ResponseEntity.ok(service.findAllShows());
  }

  @Operation(summary = "Find by the Title.", description = "Retrieve a list with matching titles")
  @GetMapping("/{title}")
  public ResponseEntity<Iterable<Show>> findShowByTitle(@PathVariable String title) {
    return ResponseEntity.ok(service.findShowByTitle(title));
  }

  @Operation(summary = "Add a show to the user watched list.", description = "If the show is not present in the database it will search in an external API and retrieve the movie/series data.")
  @PostMapping
  public ResponseEntity<Show> addShow(Integer user_id,@RequestBody Show show) {
    return ResponseEntity.ok(service.addShow(user_id, show));
  }

  @Operation(summary = "Remove a show from the user list.")
  @PutMapping
  public ResponseEntity<Show> removeShow(Integer user_id, @RequestBody Show show) {
    service.removeShow(user_id, show);
    return ResponseEntity.ok().build();
  }
}
